package com.example.thecoffeehouse.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RewardPoints")
public class RewardPoints {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "total_points")
    private int totalPoints;

    public RewardPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
