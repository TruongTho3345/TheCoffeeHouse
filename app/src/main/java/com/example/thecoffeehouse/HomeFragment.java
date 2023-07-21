package com.example.thecoffeehouse;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CoffeeAdapter.OnClickListener {
    private String param1;
    private String param2;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString("param1");
            param2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance(String param1,
                                            String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
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

/*        coffeeAdapter.setOnClickListener(new CoffeeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Coffee coffeePosition) {
                Intent intent = new Intent();
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra("coffee_details_screen", coffeePosition);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onClick(int position, Coffee coffeePosition) {
        // Create an Intent to start the CoffeeDetail activity
        Intent intent = new Intent(getContext(), CoffeeDetails.class);
        // Passing the data to the CoffeeDetailActivity
        intent.putExtra("coffee_details_screen", coffeePosition);
        startActivity(intent);
    }
}