package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.dao.RewardPointsDao;
import com.example.thecoffeehouse.entities.RewardPoints;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RedeemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_redeem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        Button redeemButton1 = view.findViewById(R.id.redeem_button1);
        Button redeemButton2 = view.findViewById(R.id.redeem_button2);
        Button redeemButton3 = view.findViewById(R.id.redeem_button3);
        redeemButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deduct points for the coffee (1340 points)
                new Thread(() -> {
                    int pointsToDeduct = 1340;
                    RewardPointsDao rewardPointsDao = AppDatabase.getInstance(requireContext()).rewardPointsDao();
                    if (rewardPointsDao.getRewardPoints() < pointsToDeduct){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Do not have enough points", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        RewardPoints rewardPoints = new RewardPoints(rewardPointsDao.getRewardPoints() - pointsToDeduct);
                        rewardPointsDao.insertRewardPoints(rewardPoints);
                    }
                }).start();

            }
        });
        redeemButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deduct points for the coffee (1340 points)
                new Thread(() -> {
                    int pointsToDeduct = 1340;
                    RewardPointsDao rewardPointsDao = AppDatabase.getInstance(requireContext()).rewardPointsDao();
                    if (rewardPointsDao.getRewardPoints() < pointsToDeduct){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Do not have enough points", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        RewardPoints rewardPoints = new RewardPoints(rewardPointsDao.getRewardPoints() - pointsToDeduct);
                        rewardPointsDao.insertRewardPoints(rewardPoints);
                    }
                }).start();

            }
        });
        redeemButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deduct points for the coffee (1340 points)
                new Thread(() -> {
                    int pointsToDeduct = 1340;
                    RewardPointsDao rewardPointsDao = AppDatabase.getInstance(requireContext()).rewardPointsDao();
                    if (rewardPointsDao.getRewardPoints() < pointsToDeduct){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "Do not have enough points", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        RewardPoints rewardPoints = new RewardPoints(rewardPointsDao.getRewardPoints() - pointsToDeduct);
                        rewardPointsDao.insertRewardPoints(rewardPoints);
                    }
                }).start();

            }
        });
    }
    public static RedeemFragment newInstance() {
        return new RedeemFragment();
    }
}