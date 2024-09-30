package com.example.rosyrecipebox.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import java.util.Date;

@Entity(tableName = "plan_meals")
public class PlanMeal {


    @PrimaryKey(autoGenerate = true)
    public int id;  // The ID for this plan meal

    @Embedded  // Store the entire Meal object within the PlanMeal table
    public Meal meal;

    @TypeConverters(DataConverter.class)  // Convert Date to long when storing in the database
    public Date mealDate;  // Date when the meal is planned

 // Store the day of the week (e.g., "Monday", "Tuesday")

    // Constructor, getters, and setters...

    public PlanMeal(Meal meal, Date mealDate) {
        this.meal = meal;
        this.mealDate = mealDate;
    }

    // Getters and Setters...
}
