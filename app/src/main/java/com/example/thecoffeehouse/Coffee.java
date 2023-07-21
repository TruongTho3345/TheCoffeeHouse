package com.example.thecoffeehouse;

import java.io.Serializable;

public class Coffee implements Serializable {
    private String coffeeName;
    private int coffeeImgId;

    public Coffee(String coffeeName, int coffeeImgId) {
        this.coffeeName = coffeeName;
        this.coffeeImgId = coffeeImgId;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getCoffeeImgId() {
        return coffeeImgId;
    }

    public void setCoffeeImgId(int coffeeImgId) {
        this.coffeeImgId = coffeeImgId;
    }
}
