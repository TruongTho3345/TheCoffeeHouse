package com.example.thecoffeehouse.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.thecoffeehouse.models.Coffee;
import com.example.thecoffeehouse.models.Constants;
import com.example.thecoffeehouse.models.Cups;
import com.example.thecoffeehouse.adapters.CupsAdapter;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.activities.CoffeeDetails;
import com.example.thecoffeehouse.adapters.CoffeeAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CoffeeAdapter.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // getting the lists
        ArrayList<Cups> cupsList = Constants.getCupsData();
        ArrayList<Coffee> coffeeList = Constants.getCoffeeData();

        // Set the LayoutManager that
        // this RecyclerView will use.
        RecyclerView cupsRecyclerView = view.findViewById(R.id.cup_recycleView);
        cupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView coffeeRecyclerView = view.findViewById(R.id.coffee_recycleView);
        coffeeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        // Assign cupsList to ItemAdapter
        CupsAdapter cupsAdapter = new CupsAdapter(cupsList);
        CoffeeAdapter coffeeAdapter = new CoffeeAdapter(coffeeList);
        // adapter instance is set to the
        // recyclerview to inflate the items.
        cupsRecyclerView.setAdapter(cupsAdapter);
        coffeeRecyclerView.setAdapter(coffeeAdapter);

        // Set OnClickListener to handle coffee item click events
        coffeeAdapter.setOnClickListener(this);

        ImageButton myCartButton = view.findViewById(R.id.myCart);
        myCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                navigateToMyCartFragment();
            }
        });

    }

    @Override
    public void onClick(int position, Coffee coffeePosition) {
        // Create an Intent to start the CoffeeDetail activity
        Intent intent = new Intent(getContext(), CoffeeDetails.class);
        // Passing the data to the CoffeeDetailActivity
        intent.putExtra("coffee_details_screen", coffeePosition);
        startActivity(intent);
    }

/*    private void navigateToMyCartFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_myCartFragment);
    }*/
}