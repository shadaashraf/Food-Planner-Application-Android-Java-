package com.example.rosyrecipebox.WeekPlanner.view;

import com.example.rosyrecipebox.home.view.FavoriteStatusCallback;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.PlanMeal;

public interface WeekPlannerOnclickListener {
    public void SaveMeal(Meal meal);
    public void DeleteMeal(Meal meal);
    public void OpenDetails(Meal meal);
    public void removeMealFromPlan(PlanMeal meal);
   public void isFavorite(String id, FavoriteStatusCallback callback);

}
