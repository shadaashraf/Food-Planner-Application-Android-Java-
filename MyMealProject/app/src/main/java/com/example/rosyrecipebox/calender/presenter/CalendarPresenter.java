package com.example.rosyrecipebox.calender.presenter;

import com.example.rosyrecipebox.calender.view.CalendarDialogView;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.model.PlanMeal;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;

import java.util.Date;
import java.util.List;

public class CalendarPresenter implements MealsNetworkCallback {

    private CalendarDialogView planView;
    private  MealsRepositoryImpl  mealsRepository;

    public CalendarPresenter(CalendarDialogView view, MealsRepositoryImpl  repository)  {
        this.planView = view;
        this.mealsRepository = repository;
    }
    public void getMeals(String id)
    {
        mealsRepository.getAllMeals(this,"byId",id);
    }

    public void addToPlan(PlanMeal plan, Date date) {

        try {
            mealsRepository.insertPlanMeal(plan);

        } catch (Exception e) {

            planView.showErrMsg(e.getMessage());
        }
    }


    @Override
    public void onSuccessResult(List<Meal> meals) {
        planView.showData(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
            planView.showErrMsg(errorMsg);
    }
}
