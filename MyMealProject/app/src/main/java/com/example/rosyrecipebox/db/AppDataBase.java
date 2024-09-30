package com.example.rosyrecipebox.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.rosyrecipebox.model.DataConverter;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.PlanMeal;


@Database(entities = {Meal.class, PlanMeal.class}, version = 1)
@TypeConverters({DataConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealDAO getMealDAO();

    public static synchronized  AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"mealsdb")
                    .build();
        }
        return instance;
    }
}
