package com.example.rosyrecipebox.saved.presenter;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.rosyrecipebox.home.view.HomeViewInterface;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;
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
         return  storedMeals=repo.getStoredProducts();

    }

    public void addToSaved(Meal meal)
    {
        repo.insertProduct(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteProduct(meal);
    }




}
