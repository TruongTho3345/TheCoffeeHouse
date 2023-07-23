package com.example.thecoffeehouse.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.thecoffeehouse.entities.ProfileEntity;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(ProfileEntity profileEntity);

    @Query("SELECT * FROM profile WHERE field = :field")
    ProfileEntity getProfile(String field);

    @Query("SELECT * FROM profile")
    List<ProfileEntity> getAllProfiles();
}
