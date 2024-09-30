package com.example.rosyrecipebox.home.view;

import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;
import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface HomeViewInterface {
    public void showData(List<Meal> meal);
    public void showErrMsg(String error);
    public void IngredientsShowData(List<Ingridients> ingridients);
    public void CategoryShowData(List<Category> ingridients);
    public void AreaShowData(List<Area> ingridients);
}
