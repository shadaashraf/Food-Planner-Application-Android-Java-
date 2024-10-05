package com.example.rosyrecipebox.search.view;

import com.example.rosyrecipebox.model.Meal;


import java.util.List;

public interface SearchViewInterface {
    public void ShowData(List<Meal> meals);
    public void showErrMsg(String error);



}
