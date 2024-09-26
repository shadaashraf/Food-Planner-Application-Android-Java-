package com.example.rosyrecipebox.search.view;

import com.example.rosyrecipebox.model.Meal;

public interface ViewAllOnclickListener {
    public void SaveMeal(Meal meal);
    public void DeleteMeal(Meal meal);
    public void OpenDetails(Meal meal);

}
