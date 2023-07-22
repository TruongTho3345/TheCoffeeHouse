package com.example.thecoffeehouse.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// CartItem.java
@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "coffee_img")
    private int coffeeImg;

    @ColumnInfo(name = "coffee_name")
    private String coffeeName;

    @ColumnInfo(name = "shot")
    private String shot;

    @ColumnInfo(name = "temp")
    private String temp;

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "ice_level")
    private String iceLevel;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "price")
    private double price;

    public CartItem(long id, int coffeeImg, String coffeeName, String shot, String temp, String size, String iceLevel, int quantity, double price) {
        this.id = id;
        this.coffeeImg = coffeeImg;
        this.coffeeName = coffeeName;
        this.shot = shot;
        this.temp = temp;
        this.size = size;
        this.iceLevel = iceLevel;
        this.quantity = quantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCoffeeImg() {
        return coffeeImg;
    }

    public void setCoffeeImg(int coffeeImg) {
        this.coffeeImg = coffeeImg;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getShot() {
        return shot;
    }

    public void setShot(String shot) {
        this.shot = shot;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIceLevel() {
        return iceLevel;
    }

    public void setIceLevel(String iceLevel) {
        this.iceLevel = iceLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

