package com.example.thecoffeehouse.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.thecoffeehouse.activities.MainActivity;
import com.example.thecoffeehouse.dao.RewardPointsDao;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;
import com.example.thecoffeehouse.entities.ProfileEntity;
import com.example.thecoffeehouse.entities.RewardPoints;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class RedeemFragment extends Fragment {

    interface AddressCallback {
        void onAddressReceived(String address);
    }

    private void getAddressWithLargestId(MyCartFragment.AddressCallback callback) {
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

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM | hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }

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
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new GiftFragment())
                        .commit();

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.VISIBLE);
                bottomNavigationView.setSelectedItemId(R.id.Gifts);
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

                        getAddressWithLargestId(new MyCartFragment.AddressCallback() {
                            @Override
                            public void onAddressReceived(String address) {
                                new Thread(() -> {
                                    AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
                                    // Create an Order instance with the current date, cart item price, and address
                                    Order order = new Order(getCurrentDateTime(), 0, address, "Cafe Latte", 1);
                                    // Insert the order into the database
                                    appDatabase.orderDao().insertOrder(order);
                                }).start();
                            }
                        });
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

                        getAddressWithLargestId(new MyCartFragment.AddressCallback() {
                            @Override
                            public void onAddressReceived(String address) {
                                new Thread(() -> {
                                    AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
                                    // Create an Order instance with the current date, cart item price, and address
                                    Order order = new Order(getCurrentDateTime(), 0, address, "Flat White", 1);
                                    // Insert the order into the database
                                    appDatabase.orderDao().insertOrder(order);
                                }).start();
                            }
                        });
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

                        getAddressWithLargestId(new MyCartFragment.AddressCallback() {
                            @Override
                            public void onAddressReceived(String address) {
                                new Thread(() -> {
                                    AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
                                    // Create an Order instance with the current date, cart item price, and address
                                    Order order = new Order(getCurrentDateTime(), 0, address, "Cappuccino", 1);
                                    // Insert the order into the database
                                    appDatabase.orderDao().insertOrder(order);
                                }).start();
                            }
                        });
                    }
                }).start();

            }
        });
    }
    public static RedeemFragment newInstance() {
        return new RedeemFragment();
    }
}