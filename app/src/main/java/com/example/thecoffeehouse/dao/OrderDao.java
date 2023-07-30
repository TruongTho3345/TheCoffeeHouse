package com.example.thecoffeehouse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thecoffeehouse.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertOrder(Order order);

    @Query("SELECT SUM(quantity) FROM orders")
    int getOrderSize();

    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrdersLiveData();

    @Update
    void updateOrder(Order order);

    @Query("SELECT * FROM orders WHERE status = 2")
    LiveData<List<Order>> getOrdersWithStatusHistoryLiveData();

}


