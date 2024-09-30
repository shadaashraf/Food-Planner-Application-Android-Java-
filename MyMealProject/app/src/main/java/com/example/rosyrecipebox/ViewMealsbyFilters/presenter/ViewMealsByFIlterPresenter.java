package com.example.rosyrecipebox.ViewMealsbyFilters.presenter;

import com.example.rosyrecipebox.ViewMealsbyFilters.view.ViewMealsByFIlterViewInterface;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;
import com.example.rosyrecipebox.search.view.SearchViewInterface;

import java.util.List;

public class ViewMealsByFIlterPresenter implements MealsNetworkCallback{


    private ViewMealsByFIlterViewInterface view;
    MealsRepositoryImpl repo;

    public ViewMealsByFIlterPresenter(ViewMealsByFIlterViewInterface _view, MealsRepositoryImpl _repo) {
        view=_view;
        repo=_repo;
    }
    public void addToSaved(Meal meal)
    {
        repo.insertMeal(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteMeal(meal);
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
