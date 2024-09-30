package com.example.rosyrecipebox.calender.view;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rosyrecipebox.R;

public class CalendarFragment extends Fragment {

    private DateSelectedListener dateSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        // Show the DatePickerDialog when the fragment is created
        showDatePickerDialog();

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog,
                (view, year, month, dayOfMonth) -> {
                    if (year == currentYear && month == currentMonth) {
                        // Get the selected day of the week
                        calendar.set(year, month, dayOfMonth);
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                        // Get the day name (e.g., Monday, Tuesday, etc.)
                        String dayName = getDayOfWeek(dayOfWeek);

                        // Call the callback interface to pass the selected date back
                        if (dateSelectedListener != null) {
                            dateSelectedListener.onDateSelected(dayOfMonth, dayName);
                        }

                        // Close the fragment
                        getParentFragmentManager().popBackStack();

                    } else {
                        Toast.makeText(getContext(), "Only current month is editable.", Toast.LENGTH_SHORT).show();
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );

        // Set the date picker bounds to the current month
        Calendar minDate = Calendar.getInstance();
        minDate.set(Calendar.DAY_OF_MONTH, 1);

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.DAY_OF_MONTH, maxDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "";
        }
    }

    public void setDateSelectedListener(DateSelectedListener listener) {
        this.dateSelectedListener = listener;
    }

    // Callback interface
    public interface DateSelectedListener {
        void onDateSelected(int dayOfMonth, String dayOfWeek);
    }
}
