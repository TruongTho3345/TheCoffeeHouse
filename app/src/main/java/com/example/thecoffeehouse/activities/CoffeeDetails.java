package com.example.thecoffeehouse.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.fragments.MyCartFragment;
import com.example.thecoffeehouse.models.Coffee;
import com.example.thecoffeehouse.R;

public class CoffeeDetails extends AppCompatActivity {
    private AppDatabase appDatabase;
    private int numberOfItems = 1; // The default number of coffee items
    private int temp = 1; //0: hot, 1: cold
    private int iced = 1; //0: less, 1: normal, 2: full
    private double pricePerCoffee = 3.00;
    private double priceShot = 0.00;

    private double priceSize = 0.00;
    private double totalPricePerItem = 0;

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

        ImageButton myCartButton = findViewById(R.id.myCart);
        myCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMyCartFragment();
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

                temp = 0;
            }
        });

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cold.setImageResource(R.drawable.select_cold);
                cold.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.black), PorterDuff.Mode.SRC_IN);

                hot.setImageResource(R.drawable.select_hot);
                hot.setColorFilter(ContextCompat.getColor(CoffeeDetails.this, R.color.chooseButton), PorterDuff.Mode.SRC_IN);

                temp = 1;
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

                iced = 0;
            }
        });
        normalIceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                noneIceTextView.setBackgroundTintList(null);
                fullIceTextView.setBackgroundTintList(null);

                iced = 1;
            }
        });
        fullIceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullIceTextView.setBackgroundTintList(ContextCompat.getColorStateList(CoffeeDetails.this, R.color.chooseButton));
                noneIceTextView.setBackgroundTintList(null);
                normalIceTextView.setBackgroundTintList(null);

                iced = 2;
            }
        });

        // Initialize the Room Database instance
        appDatabase = AppDatabase.getInstance(this);

        TextView addToCart = findViewById(R.id.coffeeDetails_addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the addToCart method to add the selected coffee to the cart
                addToCart(coffeePosition, numberOfItems);
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


    private void navigateToMyCartFragment() {
        // Disable clicks on CoffeeDetails before navigating to MyCartFragment
        setClickableState(false);


        MyCartFragment myCartFragment = new MyCartFragment();

        // Set the flag indicating the source of navigation
        Bundle args = new Bundle();
        args.putString("source", "CoffeeDetailsActivity");
        myCartFragment.setArguments(args);


        // Replace HomeFragment with MyCartFragment
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flCoffeeDetails, myCartFragment)
                .addToBackStack(null)
                .commit();

    }

    private void calculateTotalPrice() {
        double totalPrice = (pricePerCoffee + priceShot + priceSize) * numberOfItems;
        totalPricePerItem = totalPrice;
        TextView totalPriceTextView = findViewById(R.id.coffeeDetails_price);
        totalPriceTextView.setText("$" + String.format("%.2f", totalPrice));
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Close the Room Database properly
        AppDatabase.destroyInstance();
    }

    @Override
    public void onBackPressed() {
        // Re-enable clicks on CoffeeDetails when coming back from MyCartFragment
        setClickableState(true);
        super.onBackPressed();
    }



    private void addToCart(Coffee selectedCoffee, int quantity) {
        // Get the selected customization details
        String shot = (priceShot == 0.00) ? "single" : "double";
        String tempCoffee = (temp == 0) ? "hot" : "cold";
        String size;
        if (priceSize == 0.00) {
            size = "small";
        } else if (priceSize == 0.50) {
            size = "medium";
        } else {
            size = "large";
        }
        String iceLevel;
        if (iced == 0){
            iceLevel = "none ice";
        } else if (iced == 1){
            iceLevel = "normal ice";
        } else {
            iceLevel = "full ice";
        }

        // Create a new cart item with the selected coffee details and quantity
        CartItem cartItem = new CartItem(0, 0, "coffeeName",
                "shot", "temp", "size", "icedLevel", 0, 0.00);
        cartItem.setCoffeeImg(selectedCoffee.getCoffeeImgId());
        cartItem.setCoffeeName(selectedCoffee.getCoffeeName());
        cartItem.setShot(shot);
        cartItem.setTemp(tempCoffee);
        cartItem.setSize(size);
        cartItem.setIceLevel(iceLevel);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(totalPricePerItem);

        // Save the cart item to the Room Database
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.cartItemDao().insertCartItem(cartItem);
            }
        }).start();

        // Optionally, you can display a message indicating the item has been added to the cart.
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }

    private void setClickableState(boolean clickable) {
        findViewById(R.id.minusIcon).setEnabled(clickable);
        findViewById(R.id.plusIcon).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_singleShot).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_doubleShot).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_hot).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_cold).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_small).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_medium).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_large).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_noneIce).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_normalIce).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_fullIce).setEnabled(clickable);
        findViewById(R.id.coffeeDetails_addToCart).setEnabled(clickable);
    }

}