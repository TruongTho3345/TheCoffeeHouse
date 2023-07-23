package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.example.thecoffeehouse.adapters.MyCartAdapter;
import com.example.thecoffeehouse.entities.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment implements MyCartAdapter.CartItemDeletedListener{

    LiveData<List<CartItem>> cartItemsLiveData;
    MyCartAdapter myCartAdapter;
    RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
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
                // Display the OrderConfirmationFragment
                OrderConfirmationFragment orderConfirmationFragment = new OrderConfirmationFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flFragment, orderConfirmationFragment)
                        .addToBackStack(null)
                        .commit();
                ConstraintLayout myCartFragment = requireActivity().findViewById(R.id.flMyCart);
                myCartFragment.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCartItemDeleted(int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
                CartItem itemToDelete = myCartAdapter.getItemAtPosition(position);
                // Delete the item from the Room database using DAO
                appDatabase.cartItemDao().deleteCartItem(itemToDelete);

            }
        }).start();
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

    // New method to update the adapter with new data
    private void updateCartData(List<CartItem> data) {
        if (myCartAdapter != null) {
            myCartAdapter.updateData(data);
        }
    }

}