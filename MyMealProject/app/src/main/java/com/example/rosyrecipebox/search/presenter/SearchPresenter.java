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

public class SearchPresenter implements MealsNetworkCallback{


    private SearchViewInterface view;
    MealsRepositoryImpl repo;

    public SearchPresenter(SearchViewInterface _view,MealsRepositoryImpl _repo) {
        view=_view;
        repo=_repo;
    }
    public void addToSaved(Meal meal)
    {
        repo.insertProduct(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteProduct(meal);
    }


    public void getProducts(String dataType,String filterValue)
    {
        repo.getAllMeals(this,dataType,filterValue);
    }



    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.ShowData(meals);
    }


    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }

}
