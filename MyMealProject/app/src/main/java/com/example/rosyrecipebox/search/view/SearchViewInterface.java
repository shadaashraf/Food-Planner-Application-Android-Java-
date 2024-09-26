package com.example.rosyrecipebox.search.view;

import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;


import java.util.List;

public interface SearchViewInterface {
    public void IngredientsShowData(List<Ingridients> ingridients);
    public void showErrMsg(String error);
    public void CategoryShowData(List<Category> ingridients);

    public void AreaShowData(List<Area> ingridients);

}
