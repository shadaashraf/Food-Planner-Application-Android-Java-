package com.example.rosyrecipebox.calender.view;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;

import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.calender.presenter.CalendarPresenter;

import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.Meal;

import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.model.PlanMeal;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import java.util.List;


public class CalenderFragment extends DialogFragment implements  CalendarDialogView {
   CalendarView calendar;
    private Meal meal;
    private CalendarPresenter calendarPresenter;

    public CalenderFragment (Meal _meal) {
        meal = _meal;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calender, container, false);
        calendar = v.findViewById(R.id.calendarView);
        Calendar selectedDateCalendar = Calendar.getInstance();
        calendar.setMinDate(System.currentTimeMillis());

        // Set max date to 6 days from now
        selectedDateCalendar.add(Calendar.DAY_OF_YEAR, 6);
        long maxDate = selectedDateCalendar.getTimeInMillis();
        calendar.setMaxDate(maxDate);

        // Initialize PlanPresenter
        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance( AreaRemoteDataSourceImpl.getInstance(), MealsRemoteDataSourceImpl.getInstance(), IngridientsRemoteDataSourceImpl.getInstance(), CategoryRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));

        calendarPresenter=new CalendarPresenter(this,repo);

        // Handle calendar date change
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int selectedYear, int selectedMonth, int selectedDay) {
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);
                selectedDateCalendar.set(Calendar.MILLISECOND, 0);
                PlanMeal planMeal = new PlanMeal(meal, selectedDateCalendar.getTime());

                // Add meal to plan and provide feedback
                calendarPresenter.addToPlan(planMeal, selectedDateCalendar.getTime());
                Toast.makeText(getContext(), "Added to plan: " + selectedDateCalendar.getTime(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up references if needed
    }

    @Override
    public void showData(LiveData<List<PlanMeal>> meals) {

    }

    @Override
    public void showErrMsg(String error) {
        Log.i("PlanMeal", "showErrMsg: ");
    }
}
