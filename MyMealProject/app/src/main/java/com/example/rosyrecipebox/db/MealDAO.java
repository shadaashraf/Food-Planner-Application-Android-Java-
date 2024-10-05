package com.example.rosyrecipebox.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.PlanMeal;

import java.util.Date;
import java.util.List;

@Dao
public interface MealDAO {

    @Query("SELECT * FROM Meal_table")
    LiveData<List<Meal>> getAllMeal();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM Meal_table WHERE idMeal= :id")
    LiveData<Meal> FindMealById(String id);

    @Query("SELECT * FROM plan_meals WHERE mealDate = :date AND type = :type")
    LiveData<List<PlanMeal>> getAllPlanMeal(Date date, char type);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlanMeal(PlanMeal planMeal);

    @Delete
    void deletePlanMeal(PlanMeal planMeal);



}
