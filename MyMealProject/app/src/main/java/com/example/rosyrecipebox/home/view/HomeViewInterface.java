package com.example.rosyrecipebox.home.view;

import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface HomeViewInterface {
    public void showData(List<Meal> meal);
    public void showErrMsg(String error);
}
