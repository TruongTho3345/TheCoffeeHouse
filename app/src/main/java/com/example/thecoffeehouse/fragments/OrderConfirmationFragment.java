package com.example.thecoffeehouse.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.activities.CoffeeDetails;
import com.example.thecoffeehouse.activities.MainActivity;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class OrderConfirmationFragment extends Fragment {

    public interface OnTrackOrderClickListener {
        void onTrackOrderClicked();
    }

    private OnTrackOrderClickListener onTrackOrderClickListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Check if the context (activity) implements the OnTrackOrderClickListener interface
        if (context instanceof OnTrackOrderClickListener) {
            onTrackOrderClickListener = (OnTrackOrderClickListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnTrackOrderClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button trackOrderButton = view.findViewById(R.id.orderConfirmation_trackMyOrderButton);
        trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrackOrderClickListener.onTrackOrderClicked();
            }
        });

    }

}