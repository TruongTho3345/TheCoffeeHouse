package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.MyOrderAdapter;
import com.example.thecoffeehouse.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyOrderAdapter myOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);

        // Initialize the RecyclerView and adapter
        recyclerView = rootView.findViewById(R.id.myOrder_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myOrderAdapter = new MyOrderAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(myOrderAdapter);

        // Retrieve order items as LiveData from the Room Database
        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        LiveData<List<Order>> ordersLiveData = appDatabase.orderDao().getAllOrdersLiveData();

        // Observe the LiveData and update the UI when it changes
        ordersLiveData.observe(getViewLifecycleOwner(), orders -> {
            // Update the RecyclerView adapter with the new order items
            myOrderAdapter.updateData(orders);
        });

        return rootView;
    }
}