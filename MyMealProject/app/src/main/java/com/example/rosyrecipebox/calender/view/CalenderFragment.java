package com.example.rosyrecipebox.calender.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

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

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalenderFragment extends DialogFragment implements CalendarDialogView {

    CalendarView calendar;
    private Meal meal;
    private CalendarPresenter calendarPresenter;
    private char mealType;
    private ImageButton breakfast;
    private ImageButton dinner;
    private ImageButton lunch;
    private ImageButton dessert;
    private ImageButton closeButton;
    private Meal detaildMeal;
    private SimpleDateFormat dateFormat;
    private Date selectedDate;

    public CalenderFragment(Meal _meal) {
        meal = _meal;
        // Initialize date format to format the date without time
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calender, container, false);
        calendar = v.findViewById(R.id.calendarView);
        Calendar selectedDateCalendar = Calendar.getInstance();
        calendar.setMinDate(System.currentTimeMillis());
        mealType = ' ';

        // Set max date to 6 days from now
        selectedDateCalendar.add(Calendar.DAY_OF_YEAR, 6);
        long maxDate = selectedDateCalendar.getTimeInMillis();
        calendar.setMaxDate(maxDate);

        breakfast = v.findViewById(R.id.breakfastButton);
        lunch = v.findViewById(R.id.lunchButton);
        dinner = v.findViewById(R.id.dinnerButton);
        dessert = v.findViewById(R.id.dessertButton);
        closeButton = v.findViewById(R.id.closeButton);

        // Initialize the presenter
        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(
                AreaRemoteDataSourceImpl.getInstance(),
                MealsRemoteDataSourceImpl.getInstance(),
                IngridientsRemoteDataSourceImpl.getInstance(),
                CategoryRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())
        );

        calendarPresenter = new CalendarPresenter(this, repo);
        calendarPresenter.getMeals(meal.idMeal);

        closeButton.setOnClickListener(view -> dismiss());

        // Set meal type
        breakfast.setOnClickListener(v1 -> mealType = 'b');
        dinner.setOnClickListener(v12 -> mealType = 'd');
        lunch.setOnClickListener(v13 -> mealType = 'l');
        dessert.setOnClickListener(v14 -> mealType = 's');

        // Handle calendar date change
        calendar.setOnDateChangeListener((calendarView, selectedYear, selectedMonth, selectedDay) -> {
            if (mealType == ' ') {
                Toast.makeText(getContext(), "Please select the meal type first", Toast.LENGTH_SHORT).show();
            } else {
                // Set selected date and time
                selectedDateCalendar.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);
                selectedDateCalendar.set(Calendar.MILLISECOND, 0);

                // Get the selected date as a `Date` object
                selectedDate = selectedDateCalendar.getTime();

                // Create the PlanMeal object
                PlanMeal planMeal = new PlanMeal(detaildMeal, selectedDate, mealType);

                // Add meal to the plan
                calendarPresenter.addToPlan(planMeal, selectedDate);

                // Show a toast message to confirm the plan addition
                Toast.makeText(getContext(), "Added to plan: " + mealType + " on " + selectedDate, Toast.LENGTH_SHORT).show();

                // Save the event to the device's calendar
                saveMealToCalendar(detaildMeal.strMeal, selectedDate,detaildMeal.strArea+" | "+detaildMeal.strCategory);
            }
        });

        return v;
    }

    @Override
    public void showData(List<Meal> meals) {
        detaildMeal = meals.get(0);
    }

    @Override
    public void showErrMsg(String error) {
        Log.i("PlanMeal", "showErrMsg: " + error);
    }

    // Method to save the meal to the device's calendar
    private void saveMealToCalendar(String mealTitle, Date selectedDate,String Description) {
        // Convert Date to milliseconds
        long startTime = selectedDate.getTime();
        long endTime = startTime + 7 * 24 * 60 * 60 * 1000;  // 7 days in milliseconds

        // Create the intent to insert the event
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, mealTitle)
                .putExtra(CalendarContract.Events.DESCRIPTION,  Description)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        // Check if any app can handle the intent and launch it
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No calendar app available", Toast.LENGTH_SHORT).show();
        }
    }
}
