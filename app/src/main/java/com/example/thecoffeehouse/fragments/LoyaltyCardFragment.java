package com.example.thecoffeehouse.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.Constants;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.CupsAdapter;
import com.example.thecoffeehouse.models.Cups;

import java.util.ArrayList;

public class LoyaltyCardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loyalty_card, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Cups> cupsList = Constants.getCupsData();
        RecyclerView cupsRecyclerView = view.findViewById(R.id.cup_recycleView);
        cupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CupsAdapter cupsAdapter = new CupsAdapter(cupsList);
        cupsRecyclerView.setAdapter(cupsAdapter);

        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return appDatabase.orderDao().getOrderSize()%8;
            }
            @Override
            protected void onPostExecute(Integer orderSize) {
                cupsAdapter.setOrderSize(orderSize);
                // Update the UI on the main thread with the result
                TextView loyaltyCapacity = view.findViewById(R.id.loyalty_capacity);
                loyaltyCapacity.setText(orderSize + "/8");
            }
        }.execute();

    }




}