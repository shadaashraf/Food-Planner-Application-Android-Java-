package com.example.rosyrecipebox.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.MealDetails.view.MealDetailFragment;
import com.example.rosyrecipebox.R;

import com.example.rosyrecipebox.calender.view.CalenderFragment;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.home.presenter.HomePresenter;
import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements SaveOnclickListener, HomeViewInterface {

    private RecyclerView recyclerView;
    private HomeRandomAdapter homeAdapter;
    private HomePresenter homePresenter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerViewArea;
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewIngredients;
    private IngredientsAdapter ingredientsAdapter;
    private AreaAdapter areaAdapter;
    private CategoryAdapter categoryAdapter;

    // Override the onCreateView method to inflate the fragment's layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false);

    }

    // Override onViewCreated to initialize UI elements after the view is created
    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance( AreaRemoteDataSourceImpl.getInstance(), MealsRemoteDataSourceImpl.getInstance(), IngridientsRemoteDataSourceImpl.getInstance(), CategoryRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));

        // Initialize UI elements
        initUI(view);
        // Set up adapters for each RecyclerView
        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>());
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        areaAdapter = new AreaAdapter(getContext(), new ArrayList<>());
        homeAdapter = new HomeRandomAdapter(getContext(), new ArrayList<>(), this);
        // Set layout managers
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewArea.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Set adapters
        recyclerViewIngredients.setAdapter(ingredientsAdapter);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewArea.setAdapter(areaAdapter);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);


        // Initialize presenter and load products
        homePresenter = new HomePresenter(HomeFragment.this, repo);
        homePresenter.getProducts();
        homePresenter.getAllCategory();
        homePresenter.getAllArea();
        homePresenter.getAllIngredients();

    }

    // Method to initialize UI components

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.first_recycler_view);
        recyclerViewIngredients = view.findViewById(R.id.Ingredient_recycler_view);
        recyclerViewCategory = view.findViewById(R.id.second_recycler_view);
        recyclerViewArea = view.findViewById(R.id.third_recycler_view);
    }
    // Method to display data when it is available
    @Override
    public void showData(List<Meal> products) {
        homeAdapter.setList(products);
        homeAdapter.notifyDataSetChanged();
    }




    // Method to save a meal (add to favorites)
    @Override
    public void SaveMeal(Meal meal) {
        homePresenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();
    }

    // Method to delete a meal (remove from favorites)
    @Override
    public void DeleteMeal(Meal meal) {
        homePresenter.removeFromSaved(meal);
        Toast.makeText(getContext(), "Meal Removed From Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OpenDetails(Meal meal) {
        MealDetailFragment mealDetailFragment=new MealDetailFragment(meal);

        // Navigate to the new Fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, mealDetailFragment); // Replace with your FragmentContainerView ID
        transaction.addToBackStack(null); // Optional: add to back stack
        transaction.commit();
    }

    @Override
    public void openCalendarDialog(Meal meal) {
        CalenderFragment myCalendar = new CalenderFragment(meal);
        myCalendar.show(getChildFragmentManager(),"CalenderFragment");

    }


    @Override
    public void IngredientsShowData(List<Ingridients> ingridients) {
        ingredientsAdapter.setList(ingridients);
        ingredientsAdapter.notifyDataSetChanged();
        Log.i("SearchFragment", "Ingredients loaded: " + ingridients.size());
    }

    @Override
    public void CategoryShowData(List<Category> categories) {
        categoryAdapter.setList(categories);
        categoryAdapter.notifyDataSetChanged();
        Log.i("SearchFragment", "categories loaded: " + categories.size());
    }

    @Override
    public void AreaShowData(List<Area> areas) {
        areaAdapter.setList(areas);
        areaAdapter.notifyDataSetChanged();
        Log.i("SearchFragment", "areas loaded: " + areas.size());
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred: " + error, Toast.LENGTH_SHORT).show();
    }




}
