package com.example.rosyrecipebox.db;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public class MealLocalDataSourceImpl {

    private static MealLocalDataSourceImpl repo = null;
    private LiveData<List<Meal>> storedMeals;
    private MealDAO mealDAO;

    private MealLocalDataSourceImpl(Context context){
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getProductDAO();
        storedMeals = mealDAO.getAllProducts();
    }

    public static MealLocalDataSourceImpl getInstance(Context context){
            if(repo == null){
                repo = new MealLocalDataSourceImpl(context);
            }
            return repo;
    }

    public LiveData<List<Meal>> getStoredData() {

        return storedMeals;
    }

    public void delete(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteProduct(meal);
            }
        }).start();
    }

    public void insert(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertProduct(meal);
            }
        }).start();
    }



}
