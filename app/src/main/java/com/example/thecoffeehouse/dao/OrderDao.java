package com.example.thecoffeehouse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.thecoffeehouse.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertOrder(Order order);

    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrdersLiveData();
}


