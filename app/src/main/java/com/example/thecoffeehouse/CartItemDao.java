package com.example.thecoffeehouse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thecoffeehouse.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {
    @Insert
    void insertCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Query("DELETE FROM cart_items WHERE id = :itemId")
    void deleteCartItemById(long itemId);

    @Query("DELETE FROM cart_items")
    void deleteAllCartItems();

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItem>> getAllCartItemsLiveData();
}
