package com.example.rosyrecipebox.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import java.util.Date;

@Entity(
        tableName = "plan_meals",
        indices = {@Index(value = {"mealId", "mealDate", "type"}, unique = true)}  // Ensure uniqueness on mealId and mealDate
)
public class PlanMeal {


    @PrimaryKey(autoGenerate = true)
    public int id;  // The ID for this plan meal

    public char type;
    @Embedded  // Store the entire Meal object within the PlanMeal table
    public Meal meal;

  // Convert Date to long when storing in the database
    public Date mealDate;  // Date when the meal is planned
    public String mealId;  // Reference to the Meal ID

    // Store the day of the week (e.g., "Monday", "Tuesday")

    // Constructor, getters, and setters...

    public PlanMeal(Meal meal, Date mealDate,char type) {
        this.meal = meal;
        this.mealDate = mealDate;
        this.type=type;
        this.mealId = meal.idMeal;

    }

    // Getters and Setters...
}
