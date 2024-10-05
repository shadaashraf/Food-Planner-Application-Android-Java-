package com.example.rosyrecipebox.home.view;

import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;
import com.example.rosyrecipebox.model.Meal;

public interface SaveOnclickListener {
    public void SaveMeal(Meal meal);
    public void DeleteMeal(Meal meal);
    public void OpenDetails(Meal meal);
    public void OpenMealByCtegory(Category category);
    public void OpenMealByArea(Area area);
    public void OpenMealByIngridients(Ingridients ingridients);
    public void openCalendarDialog(Meal meal);
    public void isFavorite(String id, FavoriteStatusCallback callback);

}
