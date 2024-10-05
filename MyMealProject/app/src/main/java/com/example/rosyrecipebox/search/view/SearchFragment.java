package com.example.rosyrecipebox.search.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.MealDetails.view.MealDetailFragment;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.calender.view.CalenderFragment;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.home.view.FavoriteStatusCallback;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;
import com.example.rosyrecipebox.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchViewInterface, ViewAllOnclickListener {

    private SearchAdapter searchAdapter;
    private SearchPresenter searchPresenter;
    private RecyclerView recyclerView;
    private Button areaBtn;
    private Button ingredientsBtn;
    private Button categoryBtn;
    private SearchView searchView; // Change EditText to SearchView
    private String filterValue;
    private String dataValue;
    private Meal mealDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataValue = null;

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(AreaRemoteDataSourceImpl.getInstance(),
                MealsRemoteDataSourceImpl.getInstance(),
                IngridientsRemoteDataSourceImpl.getInstance(),
                CategoryRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext()));

        initUI(view);



        // Initialize presenter
        searchPresenter = new SearchPresenter(this, repo);

        // Set up SearchView listener for text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query); // Call the performSearch method on submit
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: you can choose to filter the results in real-time here
                return false; // Return false if you want the default behavior
            }
        });

        areaBtn.setOnClickListener(v -> {
            areaBtn.setBackgroundColor(getResources().getColor(R.color.pink));
            areaBtn.setTextColor(Color.WHITE);
            categoryBtn.setBackgroundColor(Color.WHITE);
            categoryBtn.setTextColor(getResources().getColor(R.color.pink));
            ingredientsBtn.setBackgroundColor(Color.WHITE);
            ingredientsBtn.setTextColor(getResources().getColor(R.color.pink));
            dataValue = "area";
        });
        ingredientsBtn.setOnClickListener(v -> {
            ingredientsBtn.setBackgroundColor(getResources().getColor(R.color.pink));
            ingredientsBtn.setTextColor(Color.WHITE);
            categoryBtn.setBackgroundColor(Color.WHITE);
            categoryBtn.setTextColor(getResources().getColor(R.color.pink));
            areaBtn.setBackgroundColor(Color.WHITE);
            areaBtn.setTextColor(getResources().getColor(R.color.pink));
            dataValue = "ingredient";
        });
        categoryBtn.setOnClickListener(v -> {
            categoryBtn.setBackgroundColor(getResources().getColor(R.color.pink));
            categoryBtn.setTextColor(Color.WHITE);
            areaBtn.setBackgroundColor(Color.WHITE);
            areaBtn.setTextColor(getResources().getColor(R.color.pink));
            ingredientsBtn.setBackgroundColor(Color.WHITE);
            ingredientsBtn.setTextColor(getResources().getColor(R.color.pink));
            dataValue = "category";
        });

        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // Span count = 2 for 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchAdapter);
    }

    private void performSearch(String query) {
        filterValue = query.trim(); // Trim whitespace from the query
        if (filterValue.isEmpty() || dataValue == null) {
            Toast.makeText(getContext(), "Please enter a valid filter to search.", Toast.LENGTH_LONG).show();
        } else {
            Log.d("Search", "Searching for: " + filterValue + " with data type: " + dataValue);
            searchPresenter.getProducts(dataValue, filterValue);
        }
    }

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.MealRecycleView);
        areaBtn = view.findViewById(R.id.AreaSearchBtn);
        categoryBtn = view.findViewById(R.id.CategSearchBtn);
        ingredientsBtn = view.findViewById(R.id.IngSearchBtn);
        searchView = view.findViewById(R.id.search_view); // Make sure your layout contains a SearchView
    }

    @Override
    public void ShowData(List<Meal> meals) {
        if(searchAdapter.ById){
            MealDetailFragment mealDetailFragment = new MealDetailFragment(meals.get(0));

            // Navigate to the new Fragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, mealDetailFragment); // Replace with your FragmentContainerView ID
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        }
        else {
            if(meals != null) {
                searchAdapter.setList(meals);
                searchAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getContext(),"You Entered Something Wrong",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show(); // Show error messages
    }

    @Override
    public void SaveMeal(Meal meal) {
        searchPresenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeleteMeal(Meal meal) {
        searchPresenter.removeFromSaved(meal);
        Toast.makeText(getContext(), "Meal Removed From Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OpenDetails(Meal meal) {

        searchPresenter.getProducts("byId",meal.idMeal);

    }
    @Override
    public void openCalendarDialog(Meal meal) {
        CalenderFragment myCalendar = new CalenderFragment(meal);
        myCalendar.show(getChildFragmentManager(),"CalenderFragment");

    }

    @Override
    public void isFavorite(String id, FavoriteStatusCallback callback) {
        searchPresenter.SearchMealById(id).observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                boolean isFavorite = meal != null; // Set favorite status based on meal presence
                callback.onResult(isFavorite); // Pass the result to the callback
            }
        });
    }

}
