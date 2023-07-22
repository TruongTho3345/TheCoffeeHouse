package com.example.thecoffeehouse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.thecoffeehouse.entities.CartItem;

// AppDatabase.java
@Database(entities = {CartItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();

    // Singleton pattern to ensure only one instance of the database is created.
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "cart_database")
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

