package com.example.thecoffeehouse.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "coffee_type") // Field for coffee type (coffee name)
    private String coffeeType;

    @ColumnInfo(name = "status") // Field for order status (on-going or history)
    private int status;


    @ColumnInfo(name = "quantity") // Field for order status (on-going or history)
    private int quantity;

    // Define constants for status values
    public static final int STATUS_ON_GOING = 1;
    public static final int STATUS_HISTORY = 2;

    public Order(String date, double price, String address, String coffeeType, int quantity) {
        this.date = date;
        this.price = price;
        this.address = address;
        this.coffeeType = coffeeType;
        this.quantity = quantity;

        // Set the initial status to on-going (1)
        this.status = STATUS_ON_GOING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
