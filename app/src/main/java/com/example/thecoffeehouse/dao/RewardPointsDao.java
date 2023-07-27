package com.example.thecoffeehouse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thecoffeehouse.entities.RewardPoints;

@Dao
public interface RewardPointsDao {
    @Insert
    void insertRewardPoints(RewardPoints rewardPoints);

    @Update
    void updateRewardPoints(RewardPoints rewardPoints);

    @Query("SELECT total_points FROM RewardPoints ORDER BY id DESC LIMIT 1")
    int getRewardPoints();
}

