package com.example.thecoffeehouse.models;

import com.example.thecoffeehouse.R;

import java.util.ArrayList;

public class Constants {
    public static ArrayList<Cups> getCupsData(){
        ArrayList<Cups> cupsList = new ArrayList<Cups>();

        int cupImageResource = R.drawable.coffee_cup_1;

        // Create Cups objects with unique resource IDs for each cup image
        for (int i = 0; i<8; i++) {
            Cups cup = new Cups(cupImageResource);
            cupsList.add(cup);
        }

        return cupsList;
    };

    public static ArrayList<Coffee> getCoffeeData(){
        ArrayList<Coffee> coffeeList = new ArrayList<Coffee>();
        int[] coffeeImageResources = {
                R.drawable.coffee_americano,
                R.drawable.coffee_cappuccino,
                R.drawable.coffee_mocha,
                R.drawable.coffee_flatwhite
        };

        String[] coffeeNames = {
                "Americano",
                "Cappuccino",
                "Mocha",
                "Flat White"
        };



        for (int i = 0; i <4; i++) {
            Coffee coffee = new Coffee(coffeeNames[i],coffeeImageResources[i]);
            coffeeList.add(coffee);
        }
        return coffeeList;
    }
}
