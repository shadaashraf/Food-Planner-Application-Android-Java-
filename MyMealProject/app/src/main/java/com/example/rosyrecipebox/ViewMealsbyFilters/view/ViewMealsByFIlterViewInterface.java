package com.example.rosyrecipebox.ViewMealsbyFilters.view;

import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface ViewMealsByFIlterViewInterface {
    public void ShowData(List<Meal> meals);
    public void showErrMsg(String error);



}
