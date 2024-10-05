package com.example.rosyrecipebox.MealDetails.presenter;

import androidx.lifecycle.LiveData;

import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;

import java.util.List;

public class MealDetailsPresenter {
    private MealsRepositoryImpl repo;

    private LiveData<List<Meal>> storedMeals;

    public MealDetailsPresenter( MealsRepositoryImpl repo) {

        this.repo = repo;
    }



    public void addToSaved(Meal meal)
    {
        repo.insertMeal(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteMeal(meal);
    }
    public LiveData<Meal> SearchMealById(String id){return repo.SearchById(id);}


}
