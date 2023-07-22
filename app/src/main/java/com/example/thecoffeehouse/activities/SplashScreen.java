package com.example.thecoffeehouse.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.activities.MainActivity;

public class SplashScreen extends AppCompatActivity {

    TextView brandname;
    LottieAnimationView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        brandname = findViewById(R.id.ordinary_co);
        logo = findViewById(R.id.animation_splash);

        brandname.animate().translationY(-200).setDuration(2000).setStartDelay(0);
        logo.animate().translationY(200).setDuration(2000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }, 3500);

    }
}