package com.example.rosyrecipebox.search.presenter;

import com.example.rosyrecipebox.home.view.HomeViewInterface;
import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaNetworkCallback;
import com.example.rosyrecipebox.network.Category.CategoryNetworkCallback;
import com.example.rosyrecipebox.network.Ingridients.IngridientsNetworkCallback;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;
import com.example.rosyrecipebox.search.view.SearchViewInterface;

import java.util.List;

public class SearchPresenter implements IngridientsNetworkCallback, CategoryNetworkCallback, AreaNetworkCallback {


    private SearchViewInterface view;
    MealsRepositoryImpl repo;

    public SearchPresenter(SearchViewInterface _view,MealsRepositoryImpl _repo) {
        view=_view;
        repo=_repo;
    }
    @Override
    public void onSuccessResultArea(List<Area> areass) {
        view.AreaShowData(areass);

    }
    public void getAllCategory(){
        repo.getAllCategory(this);
    }
    public void getAllIngredients(){
        repo.getAllIngredients(this);
    }
    public void getAllArea(){
        repo.getAllAreas(this);
    }


    @Override
    public void onSuccessResultCategory(List<Category> categories) {
        view.CategoryShowData(categories);
    }

    @Override
    public void onSuccessResultIngrediants(List<Ingridients> ingridients) {
     view.IngredientsShowData(ingridients);
    }


    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }

}
