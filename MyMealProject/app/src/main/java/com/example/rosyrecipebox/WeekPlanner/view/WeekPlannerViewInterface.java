package com.example.rosyrecipebox.WeekPlanner.view;

import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.PlanMeal;

import java.util.List;

public interface WeekPlannerViewInterface {
    public void ShowData(List<PlanMeal> meals);
    public void showErrMsg(String error);



}
