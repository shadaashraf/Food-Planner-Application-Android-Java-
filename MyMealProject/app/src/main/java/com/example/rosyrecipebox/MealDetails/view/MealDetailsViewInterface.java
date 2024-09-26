package com.example.rosyrecipebox.MealDetails.view;

import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface MealDetailsViewInterface {
    public void showData(List<Meal> meal);
    public void showErrMsg(String error);
}
