package com.example.thecoffeehouse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.HistoryRewardAdapter;
import com.example.thecoffeehouse.entities.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GiftFragment extends Fragment {

    private RecyclerView giftRecyclerView;
    private HistoryRewardAdapter historyRewardAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gift, container, false);
        giftRecyclerView = rootView.findViewById(R.id.historyRewards_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        giftRecyclerView.setLayoutManager(layoutManager);
        historyRewardAdapter = new HistoryRewardAdapter(requireContext(), new ArrayList<>());
        giftRecyclerView.setAdapter(historyRewardAdapter);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoyaltyCardFragment loyaltyCardFragment = new LoyaltyCardFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homePage_linearLayout2, loyaltyCardFragment)
                .addToBackStack(null)
                .commit();

        LiveData<List<Order>> ordersLiveData = AppDatabase.getInstance(requireContext()).orderDao().getOrdersWithStatusHistoryLiveData();
        ordersLiveData.observe(getViewLifecycleOwner(), orders -> {
            if (historyRewardAdapter != null) {
                historyRewardAdapter.updateData(orders);
            }
        });

        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return appDatabase.rewardPointsDao().getRewardPoints();
            }
            @Override
            protected void onPostExecute(Integer totalRewardPoint) {
                TextView point = view.findViewById(R.id.gift_myPoints);
                point.setText(String.valueOf(totalRewardPoint));
            }
        }.execute();

        Button redeemBtn = view.findViewById(R.id.gift_redeemBtn);
        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedeemFragment redeemFragment = new RedeemFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flGift, redeemFragment)
                        .addToBackStack(null)
                        .commit();
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });


    }

    public static GiftFragment newInstance() {
        return new GiftFragment();
    }

    public void updateHistoryRewardAdapter(List<Order> orders) {
        historyRewardAdapter.updateData(orders);
    }
}