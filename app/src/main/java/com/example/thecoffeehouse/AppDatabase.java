package com.example.thecoffeehouse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.thecoffeehouse.dao.CartItemDao;
import com.example.thecoffeehouse.dao.OrderDao;
import com.example.thecoffeehouse.dao.ProfileDao;
import com.example.thecoffeehouse.dao.RewardPointsDao;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;
import com.example.thecoffeehouse.entities.ProfileEntity;
import com.example.thecoffeehouse.entities.RewardPoints;

// AppDatabase.java
@Database(entities = {CartItem.class, ProfileEntity.class, Order.class, RewardPoints.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();
    public abstract ProfileDao profileDao();
    public abstract OrderDao orderDao();
    public abstract RewardPointsDao rewardPointsDao();

    // Singleton pattern to ensure only one instance of the database is created.
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "cart_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

