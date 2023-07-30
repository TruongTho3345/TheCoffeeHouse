package com.example.thecoffeehouse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.SharedViewModel;
import com.example.thecoffeehouse.adapters.MyCartAdapter;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;
import com.example.thecoffeehouse.entities.ProfileEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MyCartFragment extends Fragment implements MyCartAdapter.CartItemDeletedListener{

    LiveData<List<CartItem>> cartItemsLiveData;
    MyCartAdapter myCartAdapter;
    RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    interface AddressCallback {
        void onAddressReceived(String address);
    }

    private void getAddressWithLargestId(AddressCallback callback) {
        // Use AtomicInteger to store the largest ID
        AtomicInteger largestId = new AtomicInteger(-1);
        // Use AtomicReference to store the largest address
        AtomicReference<String> largestIdAddress = new AtomicReference<>(null);

        // Perform the Room database query on a background thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<ProfileEntity> profiles = AppDatabase.getInstance(requireContext()).profileDao().getAllProfiles();

                for (ProfileEntity profile : profiles) {
                    if ("address".equals(profile.getField()) && profile.getId() > largestId.get()) {
                        largestId.set(profile.getId());
                        largestIdAddress.set(profile.getEditedText());
                    }
                }
                callback.onAddressReceived(largestIdAddress.get());
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_cart, container, false);
        recyclerView = rootView.findViewById(R.id.cartRecyclerView);

        // Initialize the RecyclerView and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Create the adapter instance with an empty list
        myCartAdapter = new MyCartAdapter(requireContext(), new ArrayList<>());

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(myCartAdapter);

        // Initialize the Room Database instance
        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());

        // Retrieve cart items as LiveData from the Room Database
        cartItemsLiveData = appDatabase.cartItemDao().getAllCartItemsLiveData();

        // Observe the LiveData and update the UI when it changes
        cartItemsLiveData.observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                // Update the RecyclerView adapter with the new cart items
                myCartAdapter.updateData(cartItems);

                // Calculate and show the total price with the updated cart items
                calculateTotalPrice(getView());
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the fragment was navigated from CoffeeDetails activity
                Bundle args = getArguments();
                if (args != null && args.getString("source", "").equals("CoffeeDetailsActivity")) {
                    // Go back to CoffeeDetails activity
                    requireActivity().onBackPressed();
                } else {
                    // Default behavior - Go back to the previous fragment
                    requireActivity().getSupportFragmentManager().popBackStack();
                    BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the position of the swiped item
                int position = viewHolder.getAdapterPosition();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the adapter about the item being swiped, so it can be removed from the list
                        myCartAdapter.deleteItem(position);

                        // Calculate and show the total price after deletion
                        calculateTotalPrice(view);
                    }
                });
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);

        RelativeLayout checkoutButton = view.findViewById(R.id.myCart_checkoutBtn);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the list of cart items from the adapter
                List<CartItem> cartItems = myCartAdapter.getCartItems();

                // Get the current date as a string
                String currentDate = getCurrentDateTime();

                getAddressWithLargestId(new AddressCallback() {
                    @Override
                    public void onAddressReceived(String address) {
                        // Save each cart item as a separate order to the database using a background thread
                        new Thread(() -> {
                            AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
                            for (CartItem cartItem : cartItems) {
                                // Create an Order instance with the current date, cart item price, and address
                                Order order = new Order(currentDate, cartItem.getPrice(), address, cartItem.getCoffeeName(), cartItem.getQuantity());
                                // Insert the order into the database
                                appDatabase.orderDao().insertOrder(order);
                            }
                            // After saving the orders, delete all cart items from the database
                            appDatabase.cartItemDao().deleteAllCartItems();
                        }).start();
                    }
                });

                OrderConfirmationFragment orderConfirmationFragment = new OrderConfirmationFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flMyCart, orderConfirmationFragment);
                fragmentTransaction.addToBackStack(null); // Add the transaction to the back stack
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onCartItemDeleted(int position) {

    }



    private void calculateTotalPrice(View view) {
        double totalPrice = 0;
        if (cartItemsLiveData != null && cartItemsLiveData.getValue() != null) {
            List<CartItem> cartItems = cartItemsLiveData.getValue();
            for (CartItem cartItem : cartItems) {
                totalPrice += cartItem.getPrice();
            }
        }

        TextView totalPriceTextView = view.findViewById(R.id.myCart_totalPrice);
        totalPriceTextView.setText("$" + String.format("%.2f", totalPrice));
    }


    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM | hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }



}