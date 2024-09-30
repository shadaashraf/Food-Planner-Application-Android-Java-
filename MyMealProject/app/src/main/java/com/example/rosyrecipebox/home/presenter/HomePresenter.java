package com.example.rosyrecipebox.home.presenter;

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

import java.util.List;

public class HomePresenter implements MealsNetworkCallback,IngridientsNetworkCallback, CategoryNetworkCallback, AreaNetworkCallback {

    private MealsRepositoryImpl repo;
    private HomeViewInterface view;



    public HomePresenter (HomeViewInterface  _view,MealsRepositoryImpl _repo) {
        view = _view;
        repo = _repo;
    }

    public void getProducts()
    {
        repo.getAllMeals(this,"random","");
    }

    public void addToSaved(Meal meal)
    {
        repo.insertMeal(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteMeal(meal);
    }



    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals);
    }

    @Override
    public void onSuccessResultArea(List<Area> areass) {
            view.AreaShowData(areass);
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


    public void getAllCategory(){
        repo.getAllCategory(this);
    }
    public void getAllIngredients(){
        repo.getAllIngredients(this);
    }
    public void getAllArea() {
        repo.getAllAreas(this);

    }

    }
