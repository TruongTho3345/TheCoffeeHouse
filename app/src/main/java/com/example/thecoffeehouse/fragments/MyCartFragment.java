package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.MyCartAdapter;
import com.example.thecoffeehouse.entities.CartItem;

import java.util.List;

public class MyCartFragment extends Fragment {

    List<CartItem> cartItemList;
    MyCartAdapter myCartAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_cart, container, false);

        recyclerView = rootView.findViewById(R.id.cartRecyclerView);

        // Initialize the RecyclerView and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the Room Database instance
        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());

        // Retrieve cart items from the Room Database
        new Thread(new Runnable() {
            @Override
            public void run() {
                cartItemList = appDatabase.cartItemDao().getAllCartItems();
                // Update the UI on the main thread with the retrieved cart items
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCartAdapter = new MyCartAdapter(cartItemList);
                        recyclerView.setAdapter(myCartAdapter);
                    }
                });
            }
        }).start();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                navigateBack();*/
            }
        });
    }

/*    private void navigateBack() {
        // Pop the back stack to navigate back to the previous fragment/activity
        Navigation.findNavController(requireView()).popBackStack();
    }*/
}