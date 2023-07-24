package com.example.thecoffeehouse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.thecoffeehouse.fragments.MyOrderFragment;
import com.example.thecoffeehouse.fragments.GiftFragment;
import com.example.thecoffeehouse.fragments.HomeFragment;
import com.example.thecoffeehouse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    public interface EditDialogListener {
        void onDialogPositiveClick(String field, String editedText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    HomeFragment homeFragment = new HomeFragment();
    GiftFragment giftFragment = new GiftFragment();
    MyOrderFragment myOrderFragment = new MyOrderFragment();

    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.nav_home) {
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

        else if (item.getItemId() == R.id.nav_myOrder){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, myOrderFragment)
                        .commit();
                return true;
        }

        else return false;
    }
}