package com.example.rosyrecipebox.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rosyrecipebox.model.Meal;

import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM Meal_table")
    LiveData<List<Meal>>getAllProducts();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(Meal meal);

    @Delete
    void deleteProduct(Meal meal);



}
