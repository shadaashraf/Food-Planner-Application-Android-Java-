package com.example.rosyrecipebox.network.ListOfMeals;



import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface MealsNetworkCallback {
    public void onSuccessResult(List<Meal> meals);
    public void onFailureResult(String errorMsg);
}
