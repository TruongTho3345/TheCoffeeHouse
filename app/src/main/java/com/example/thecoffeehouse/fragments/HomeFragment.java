package com.example.thecoffeehouse.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.SharedViewModel;
import com.example.thecoffeehouse.entities.ProfileEntity;
import com.example.thecoffeehouse.models.Coffee;
import com.example.thecoffeehouse.Constants;
import com.example.thecoffeehouse.models.Cups;
import com.example.thecoffeehouse.adapters.CupsAdapter;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.activities.CoffeeDetails;
import com.example.thecoffeehouse.adapters.CoffeeAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements CoffeeAdapter.OnClickListener {
    private TextView textViewName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragments
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTextViewHello(view);

        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return appDatabase.profileDao().getStringByField("name");
            }
            @Override
            protected void onPostExecute(String name) {
                // Update the UI on the main thread with the result
                textViewName = view.findViewById(R.id.home_textViewName);
                textViewName.setText(name);
            }
        }.execute();

        // loyalty card
        ArrayList<Coffee> coffeeList = Constants.getCoffeeData();
        LoyaltyCardFragment loyaltyCardFragment = new LoyaltyCardFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homePage_linearLayout2, loyaltyCardFragment)
                .addToBackStack(null)
                .commit();

        RecyclerView coffeeRecyclerView = view.findViewById(R.id.coffee_recycleView);
        coffeeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        CoffeeAdapter coffeeAdapter = new CoffeeAdapter(coffeeList);
        coffeeRecyclerView.setAdapter(coffeeAdapter);

        // Set OnClickListener to handle coffee item click events
        coffeeAdapter.setOnClickListener(this);

        ImageButton myCartButton = view.findViewById(R.id.myCart);
        myCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCartFragment myCartFragment = new MyCartFragment();

                // Replace HomeFragment with MyCartFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flHomeFragment, myCartFragment)
                        .addToBackStack(null)
                        .commit();
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        ImageButton imageButton = view.findViewById(R.id.imageButtonProfile);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flHomeFragment, profileFragment)
                        .addToBackStack(null)
                        .commit();
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.GONE);
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

    private void setTextViewHello(View view){
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        TextView textViewHello = view.findViewById(R.id.textViewHello);

        if (hourOfDay >= 5 && hourOfDay < 12) {
            textViewHello.setText("Good Morning");
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            textViewHello.setText("Good Afternoon");
        } else if (hourOfDay >= 17 && hourOfDay < 21) {
            textViewHello.setText("Good Evening");
        } else {
            textViewHello.setText("Good Night");
        }

    }

}