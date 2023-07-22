package com.example.thecoffeehouse;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thecoffeehouse.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert
    void insertCartItem(CartItem cartItem);

    @Query("SELECT * FROM cart_items")
    List<CartItem> getAllCartItems();
}
