package com.example.rosyrecipebox.WeekPlanner.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rosyrecipebox.R;

import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;

import java.util.Calendar;
import java.util.Date;


public class WeekPlannerFragment extends Fragment {

    private ImageButton breakfast, lunch, dinner, dessert;
    private Button Sun, Mon, Tue, Wed, Thu, Fri, Sat;
    private LinearLayout breakfast_lay, lunch_lay, dinner_lay, dessert_lay;
    private String day;
    private Calendar calendar;
    private Date selectedDate; // Date to hold the selected day

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.planner, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        day = null;
        calendar = Calendar.getInstance(); // Get current date

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(AreaRemoteDataSourceImpl.getInstance(),
                MealsRemoteDataSourceImpl.getInstance(),
                IngridientsRemoteDataSourceImpl.getInstance(),
                CategoryRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext()));

        initUI(view);

        // Define day buttons click behavior
        Sun.setOnClickListener(new DayClickListener("Sun", Calendar.SUNDAY));
        Mon.setOnClickListener(new DayClickListener("Mon", Calendar.MONDAY));
        Tue.setOnClickListener(new DayClickListener("Tue", Calendar.TUESDAY));
        Wed.setOnClickListener(new DayClickListener("Wed", Calendar.WEDNESDAY));
        Thu.setOnClickListener(new DayClickListener("Thu", Calendar.THURSDAY));
        Fri.setOnClickListener(new DayClickListener("Fri", Calendar.FRIDAY));
        Sat.setOnClickListener(new DayClickListener("Sat", Calendar.SATURDAY));

        // Define meal type buttons click behavior
        breakfast.setOnClickListener(new MealClickListener('b'));
        lunch.setOnClickListener(new MealClickListener('l'));
        dinner.setOnClickListener(new MealClickListener('d'));
        dessert.setOnClickListener(new MealClickListener('s'));

        breakfast_lay.setOnClickListener(new MealClickListener('b'));
        lunch_lay.setOnClickListener(new MealClickListener('l'));
        dinner_lay.setOnClickListener(new MealClickListener('d'));
        dessert_lay.setOnClickListener(new MealClickListener('s'));
    }

    private class DayClickListener implements View.OnClickListener {
        private String dayString;
        private int calendarDay;

        public DayClickListener(String dayString, int calendarDay) {
            this.dayString = dayString;
            this.calendarDay = calendarDay;
        }

        @Override
        public void onClick(View v) {
            // Set the day in the calendar to the selected day of the week
            calendar.set(Calendar.DAY_OF_WEEK, calendarDay);

            if (isDayInPast(calendar)) {
                Toast.makeText(getContext(), "Cannot select a past day", Toast.LENGTH_LONG).show();
                return;
            }

            // Proceed if it's not in the past
            day = dayString;
            selectedDate = calendar.getTime(); // Save the actual Date object
            Toast.makeText(getContext(), selectedDate.toString(), Toast.LENGTH_LONG).show();
            resetDayButtonColors();
            ((Button) v).setBackgroundColor(getResources().getColor(R.color.pink));
            ((Button) v).setTextColor(getResources().getColor(R.color.white));
        }
    }

    private class MealClickListener implements View.OnClickListener {
        private char mealType;

        public MealClickListener(char mealType) {
            this.mealType = mealType;
        }

        @Override
        public void onClick(View v) {
            if (day == null) {
                Toast.makeText(getContext(), "Please choose a day first", Toast.LENGTH_LONG).show();
            } else {
                PlannedMealViewFragment plannedMealViewFragment = new PlannedMealViewFragment(selectedDate, mealType);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, plannedMealViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Log.d("WeekPlannerFragment", "Navigating to PlannedMealViewFragment with date: " + selectedDate + " and meal type: " + mealType);
            }
        }
    }

    // Helper function to check if the selected day is in the past
    private boolean isDayInPast(Calendar selectedDay) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        // Check if the selected day is before today
        return selectedDay.before(today);
    }

    // Resets the colors of the day buttons to their default state
    private void resetDayButtonColors() {
        Sun.setBackgroundColor(getResources().getColor(R.color.white));
        Sun.setTextColor(getResources().getColor(R.color.pink));
        Mon.setBackgroundColor(getResources().getColor(R.color.white));
        Mon.setTextColor(getResources().getColor(R.color.pink));
        Tue.setBackgroundColor(getResources().getColor(R.color.white));
        Tue.setTextColor(getResources().getColor(R.color.pink));
        Wed.setBackgroundColor(getResources().getColor(R.color.white));
        Wed.setTextColor(getResources().getColor(R.color.pink));
        Thu.setBackgroundColor(getResources().getColor(R.color.white));
        Thu.setTextColor(getResources().getColor(R.color.pink));
        Fri.setBackgroundColor(getResources().getColor(R.color.white));
        Fri.setTextColor(getResources().getColor(R.color.pink));
        Sat.setBackgroundColor(getResources().getColor(R.color.white));
        Sat.setTextColor(getResources().getColor(R.color.pink));
    }

    private void initUI(View view) {
        breakfast = view.findViewById(R.id.breakfastBtn);
        lunch = view.findViewById(R.id.lunchBtn);
        dinner = view.findViewById(R.id.dinnerBtn);
        dessert = view.findViewById(R.id.dessertBtn);
        Sun = view.findViewById(R.id.sunBtn);
        Mon = view.findViewById(R.id.monBtn);
        Tue = view.findViewById(R.id.tueBtn);
        Wed = view.findViewById(R.id.wedBtn);
        Thu = view.findViewById(R.id.thuBtn);
        Fri = view.findViewById(R.id.friBtn);
        Sat = view.findViewById(R.id.satBtn);
        breakfast_lay = view.findViewById(R.id.breakfastLayout);
        lunch_lay = view.findViewById(R.id.lunchLayout);
        dinner_lay = view.findViewById(R.id.dinnerLayout);
        dessert_lay = view.findViewById(R.id.dessertLayout);
    }

}
