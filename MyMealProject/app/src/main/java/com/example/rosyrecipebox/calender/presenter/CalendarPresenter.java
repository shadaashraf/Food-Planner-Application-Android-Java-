package com.example.rosyrecipebox.calender.presenter;

import com.example.rosyrecipebox.calender.view.CalendarDialogView;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.model.PlanMeal;

import java.util.Date;

public class CalendarPresenter {

    private CalendarDialogView planView;
    private  MealsRepositoryImpl  mealsRepository;

    public CalendarPresenter(CalendarDialogView view, MealsRepositoryImpl  repository) {
        this.planView = view;
        this.mealsRepository = repository;
    }


    public void addToPlan(PlanMeal plan, Date date) {

        try {
            mealsRepository.insertPlanMeal(plan);

            planView.showData(mealsRepository.getPlanMeal(date));
        } catch (Exception e) {

            planView.showErrMsg(e.getMessage());
        }
    }



}
