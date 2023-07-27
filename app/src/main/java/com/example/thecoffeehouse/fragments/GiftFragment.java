package com.example.thecoffeehouse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecoffeehouse.Constants;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.adapters.CoffeeAdapter;
import com.example.thecoffeehouse.models.Coffee;

import java.util.ArrayList;

public class GiftFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_gift, container, false);
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
    }
}