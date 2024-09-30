package com.example.rosyrecipebox.saved.presenter;

import androidx.lifecycle.LiveData;

import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.saved.view.SavedViewInterface;

import java.util.List;

public class SavedPresenter {

    private MealsRepositoryImpl repo;
    private SavedViewInterface  view;
    private LiveData<List<Meal>> storedMeals;
    public SavedPresenter(SavedViewInterface _view, MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    public LiveData<List<Meal>> getProducts()
    {
         return  storedMeals=repo.getStoredMeal();

    }

    public void addToSaved(Meal meal)
    {
        repo.insertMeal(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteMeal(meal);
    }




}
