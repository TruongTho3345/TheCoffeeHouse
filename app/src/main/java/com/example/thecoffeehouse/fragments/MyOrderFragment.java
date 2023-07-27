package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.HistoryRewardAdapter;
import com.example.thecoffeehouse.adapters.MyOrderAdapter;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment implements MyOrderAdapter.OnOrderClickListener {

    private RecyclerView recyclerViewOnGoing;
    private RecyclerView recyclerViewHistory;
    private MyOrderAdapter myOrderAdapterOnGoing;
    private MyOrderAdapter myOrderAdapterHistory;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_order, container, false);

        // Initialize the RecyclerView and adapter
        recyclerViewOnGoing = rootView.findViewById(R.id.myOrder_rvOnGoing);
        recyclerViewHistory = rootView.findViewById(R.id.myOrder_rvHistory);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManagerHistory = new LinearLayoutManager(getContext());

        recyclerViewOnGoing.setLayoutManager(layoutManager);
        recyclerViewHistory.setLayoutManager(layoutManagerHistory);

        myOrderAdapterOnGoing = new MyOrderAdapter(requireContext(), new ArrayList<>(), this);
        myOrderAdapterHistory = new MyOrderAdapter(requireContext(), new ArrayList<>(), this);

        recyclerViewOnGoing.setAdapter(myOrderAdapterOnGoing);
        recyclerViewHistory.setAdapter(myOrderAdapterHistory);

        recyclerViewHistory.setVisibility(View.GONE);
        recyclerViewOnGoing.setVisibility(View.VISIBLE);

        // Retrieve order items as LiveData from the Room Database
        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        LiveData<List<Order>> ordersLiveData = appDatabase.orderDao().getAllOrdersLiveData();

        // Observe the LiveData and update the UI when it changes
        ordersLiveData.observe(getViewLifecycleOwner(), orders -> {
            updateAdaptersData(orders);
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView onGoingText = view.findViewById(R.id.myOrder_onGoing_text);
        TextView historyText = view.findViewById(R.id.myOrder_history_text);

        Button onGoingBelowText = view.findViewById(R.id.myOrder_onGoing_belowText);
        Button historyBelowText = view.findViewById(R.id.myOrder_history_belowText);

        int chooseColor = ContextCompat.getColor(requireContext(), R.color.textColor);
        int notChooseColor = ContextCompat.getColor(requireContext(), R.color.chooseButton);

        ConstraintLayout myOrderHistory = view.findViewById(R.id.myOrder_history);
        myOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHistory.setVisibility(View.VISIBLE);
                recyclerViewOnGoing.setVisibility(View.GONE);

                historyText.setTextColor(chooseColor);
                historyBelowText.setBackgroundColor(chooseColor);

                onGoingText.setTextColor(notChooseColor);
                onGoingBelowText.setBackgroundColor(notChooseColor);

            }
        });

        ConstraintLayout myOrderOnGoing = view.findViewById(R.id.myOrder_onGoing);
        myOrderOnGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewHistory.setVisibility(View.GONE);
                recyclerViewOnGoing.setVisibility(View.VISIBLE);

                onGoingText.setTextColor(chooseColor);
                onGoingBelowText.setBackgroundColor(chooseColor);

                historyText.setTextColor(notChooseColor);
                historyBelowText.setBackgroundColor(notChooseColor);
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
                        myOrderAdapterOnGoing.deleteItem(position);

                    }
                });
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewOnGoing);
    }


    private void updateAdaptersData(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            // Handle the case when orders is null or empty
            return;
        }
        List<Order> onGoingOrders = new ArrayList<>();
        List<Order> historyOrders = new ArrayList<>();

        // Separate orders into on-going and history based on the status
        for (Order order : orders) {
            if (order.getStatus() == Order.STATUS_ON_GOING) {
                onGoingOrders.add(order);
            } else if (order.getStatus() == Order.STATUS_HISTORY) {
                historyOrders.add(order);
            }
        }
        myOrderAdapterOnGoing.updateData(onGoingOrders);
        myOrderAdapterHistory.updateData(historyOrders);
    }


    @Override
    public void onOrderClicked(int position) {

    }
}