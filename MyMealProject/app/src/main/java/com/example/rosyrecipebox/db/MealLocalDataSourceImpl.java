package com.example.rosyrecipebox.db;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.PlanMeal;

import java.util.Date;
import java.util.List;

public class MealLocalDataSourceImpl {

    private static MealLocalDataSourceImpl repo = null;
    private LiveData<List<Meal>> storedMeals;
    private MealDAO mealDAO;

    private MealLocalDataSourceImpl(Context context){
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getMealDAO();

    }

    public static MealLocalDataSourceImpl getInstance(Context context){
            if(repo == null){
                repo = new MealLocalDataSourceImpl(context);
            }
            return repo;
    }

    public LiveData<List<PlanMeal>> getPlanData(Date date) {

        return mealDAO.getAllPlanMeal(date);
    }
    public LiveData<List<Meal>> getStoredData() {

        return mealDAO.getAllMeal();
    }

    public void deleteMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }).start();
    }

    public void insertMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }).start();
    }
    public void deletePlanMeal(PlanMeal planMeal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deletePlanMeal(planMeal);
            }
        }).start();
    }

    public void insertPlanMeal(PlanMeal planMeal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertPlanMeal(planMeal);
            }
        }).start();
    }



}
