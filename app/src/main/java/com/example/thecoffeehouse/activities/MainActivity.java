package com.example.thecoffeehouse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.thecoffeehouse.fragments.BillFragment;
import com.example.thecoffeehouse.fragments.GiftFragment;
import com.example.thecoffeehouse.fragments.HomeFragment;
import com.example.thecoffeehouse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
    }


    HomeFragment homeFragment = new HomeFragment();
    GiftFragment giftFragment = new GiftFragment();
    BillFragment billFragment = new BillFragment();

    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.home_nav) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        }
        else if (item.getItemId() == R.id.Gifts) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, giftFragment)
                    .commit();
            return true;
        }

        else if (item.getItemId() == R.id.Bills){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, billFragment)
                        .commit();
                return true;
        }
        else return false;
    }
}