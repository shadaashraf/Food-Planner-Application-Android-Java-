package com.example.rosyrecipebox.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
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

public class ViewMealsByFIlterFragment extends Fragment implements ViewAllOnclickListener, SearchViewInterface {


    private SearchPresenter searchPresenter;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView; // Change EditText to SearchView
    private String filtrtValue;
    private TextView mytext;
    private String dataValue;


    private Meal mealDetails;

    public ViewMealsByFIlterFragment(String dataValue, String filtrtValue) {
        this.dataValue = dataValue;
        this.filtrtValue = filtrtValue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_by_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(AreaRemoteDataSourceImpl.getInstance(),
                MealsRemoteDataSourceImpl.getInstance(),
                IngridientsRemoteDataSourceImpl.getInstance(),
                CategoryRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext()));

        initUI(view);



        // Initialize presenter
        Log.i("DATA", "onViewCreated: "+filtrtValue+dataValue);
        searchPresenter = new SearchPresenter(this, repo);
        if (filtrtValue.isEmpty() || dataValue == null) {
            Toast.makeText(getContext(), "Please enter a valid filter to search.", Toast.LENGTH_LONG).show();
        } else {
            Log.d("SearchC", "Searching for: " + filtrtValue+ " with data type: " + dataValue);
            searchPresenter.getProducts(dataValue,filtrtValue);
        }


        mytext.setText(filtrtValue+" Meals");

        searchAdapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // Span count = 2 for 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(searchAdapter);
    }



    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.ViewDataRecycle);
        mytext=view.findViewById(R.id.AllCountries);
    }

    @Override
    public void ShowData(List<Meal> meals) {
        if(searchAdapter.ById){
            MealDetailFragment mealDetailFragment = new MealDetailFragment(meals.get(0));
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, mealDetailFragment); // Replace with your FragmentContainerView ID
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        }
        else {
            searchAdapter.setList(meals);
            searchAdapter.notifyDataSetChanged();
            Log.i("category4", meals.get(0).strMeal);
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
