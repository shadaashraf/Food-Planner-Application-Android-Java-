package com.example.rosyrecipebox.home.presenter;

import com.example.rosyrecipebox.home.view.HomeViewInterface;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;

import java.util.List;

public class HomePresenter implements MealsNetworkCallback {

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
        repo.insertProduct(meal);
    }
    public void removeFromSaved(Meal meal){
        repo.deleteProduct(meal);
    }



    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.showData(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrMsg(errorMsg);
    }

}
