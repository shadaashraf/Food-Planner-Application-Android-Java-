package com.example.rosyrecipebox.saved.view;

import com.example.rosyrecipebox.model.Meal;

public interface SaveOnclickListener {
    public void SaveMeal(Meal meal);
    public void DeleteMeal(Meal meal);
    public void OpenDetails(Meal meal);

}
