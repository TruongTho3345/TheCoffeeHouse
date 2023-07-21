package com.example.thecoffeehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeDetails extends AppCompatActivity {
    private int numberOfItems = 1; // The default number of coffee items
    private double pricePerCoffee = 3.00;
    private double priceShot = 0.00;

    private double priceSize = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_details);


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageSwitcher coffeeDetailsImageSwitcher = findViewById(R.id.coffeeDetails_img);
        TextView coffeeTypeTextView = findViewById(R.id.coffeeDetails_coffeeType_text);
        Coffee coffeePosition = (Coffee) getIntent().getSerializableExtra("coffee_details_screen");

        // Set the coffee image in the ImageSwitcher
        if (coffeePosition != null) {
            //set img of coffee
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(coffeePosition.getCoffeeImgId());
            coffeeDetailsImageSwitcher.addView(imageView);

            //set name of coffee
            coffeeTypeTextView.setText(coffeePosition.getCoffeeName());
        }

        TextView minusIcon = findViewById(R.id.minusIcon);
        TextView numberText = findViewById(R.id.numberText);
        TextView plusIcon = findViewById(R.id.plusIcon);

        // Set click listeners for minus and plus icons
        minusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfItems > 1) {
                    numberOfItems--;
                    numberText.setText(String.valueOf(numberOfItems));
                    calculateTotalPrice();
                }
            }
        });

        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOfItems < 100 ){
                    numberOfItems++;
                    numberText.setText(String.valueOf(numberOfItems));
                    calculateTotalPrice();
                }
            }
        });

        TextView singleShotTextView = findViewById(R.id.coffeeDetails_singleShot);
        TextView doubleShotTextView = findViewById(R.id.coffeeDetails_doubleShot);

        // Set click listeners for the single and double shot TextViews
        singleShotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceShot = 0.00;
                singleShotTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                doubleShotTextView.setBackgroundTintList(null);
                calculateTotalPrice();
            }
        });

        doubleShotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceShot = 0.50;
                doubleShotTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                singleShotTextView.setBackgroundTintList(null);
                calculateTotalPrice();
            }
        });

        ImageView hot = findViewById(R.id.coffeeDetails_hot);
        ImageView cold = findViewById(R.id.coffeeDetails_cold);

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hot.setImageResource(R.drawable.select_hot);
                hot.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                cold.setImageResource(R.drawable.select_cold);
                cold.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

            }
        });

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cold.setImageResource(R.drawable.select_cold);
                cold.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                hot.setImageResource(R.drawable.select_hot);
                hot.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

            }
        });

        ImageView small = findViewById(R.id.coffeeDetails_small);
        ImageView medium = findViewById(R.id.coffeeDetails_medium);
        ImageView large = findViewById(R.id.coffeeDetails_large);

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceSize = 0.00;
                small.setImageResource(R.drawable.size_small);
                small.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                medium.setImageResource(R.drawable.size_medium);
                medium.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                large.setImageResource(R.drawable.size_large);
                large.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                calculateTotalPrice();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceSize = 0.50;
                medium.setImageResource(R.drawable.size_medium);
                medium.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                small.setImageResource(R.drawable.size_small);
                small.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                large.setImageResource(R.drawable.size_large);
                large.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                calculateTotalPrice();
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceSize = 1.00;
                large.setImageResource(R.drawable.size_large);
                large.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                small.setImageResource(R.drawable.size_small);
                small.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                medium.setImageResource(R.drawable.size_medium);
                medium.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                calculateTotalPrice();
            }
        });

        TextView noneIceTextView = findViewById(R.id.coffeeDetails_noneIce);
        TextView normalIceTextView = findViewById(R.id.coffeeDetails_normalIce);
        TextView fullIceTextView = findViewById(R.id.coffeeDetails_fullIce);

        noneIceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noneIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                normalIceTextView.setBackgroundTintList(null);
                fullIceTextView.setBackgroundTintList(null);
            }
        });
        normalIceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                noneIceTextView.setBackgroundTintList(null);
                fullIceTextView.setBackgroundTintList(null);
            }
        });
        fullIceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                noneIceTextView.setBackgroundTintList(null);
                normalIceTextView.setBackgroundTintList(null);
            }
        });


        //default coffee
        singleShotTextView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.chooseButton));

        cold.setImageResource(R.drawable.select_cold);
        cold.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

        small.setImageResource(R.drawable.size_small);
        small.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

        normalIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));


        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double totalPrice = (pricePerCoffee + priceShot + priceSize) * numberOfItems;

        TextView totalPriceTextView = findViewById(R.id.coffeeDetails_price);
        totalPriceTextView.setText(String.format("%.2f", totalPrice));
    }

}