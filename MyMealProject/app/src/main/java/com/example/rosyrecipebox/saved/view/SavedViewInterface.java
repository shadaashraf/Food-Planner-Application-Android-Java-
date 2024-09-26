package com.example.rosyrecipebox.saved.view;

import androidx.lifecycle.LiveData;

import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public interface SavedViewInterface {
    public void showData(List<Meal> meals);
    public void showErrMsg(String error);
}
