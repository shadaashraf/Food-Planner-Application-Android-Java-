package com.example.rosyrecipebox.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.model.Ingridients;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;
import com.example.rosyrecipebox.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchViewInterface {

    private RecyclerView recyclerViewArea;
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewIngredients;
    private IngredientsAdapter ingredientsAdapter;
    private AreaAdapter areaAdapter;
    private CategoryAdapter categoryAdapter;
    private SearchPresenter searchPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MealsRemoteDataSourceImpl mealsRemoteDataSource = MealsRemoteDataSourceImpl.getInstance(); // Initialize MealsRemoteDataSourceImpl
        IngridientsRemoteDataSourceImpl ingridientsRemoteDataSource = IngridientsRemoteDataSourceImpl.getInstance(); // Initialize IngridientsRemoteDataSourceImpl
        CategoryRemoteDataSourceImpl categoryRemoteDataSource = CategoryRemoteDataSourceImpl.getInstance(); // Initialize CategoryRemoteDataSourceImpl
        AreaRemoteDataSourceImpl areaRemoteDataSource = AreaRemoteDataSourceImpl.getInstance(); // Initialize AreaRemoteDataSourceImpl

        MealLocalDataSourceImpl mealsRepository = MealLocalDataSourceImpl.getInstance(getContext()); // Initialize local data source

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(areaRemoteDataSource, mealsRemoteDataSource, ingridientsRemoteDataSource, categoryRemoteDataSource, mealsRepository);

        initUI(view);

        // Set up adapters for each RecyclerView
        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>());
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        areaAdapter = new AreaAdapter(getContext(), new ArrayList<>());

        // Set layout managers
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewArea.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Set adapters
        recyclerViewIngredients.setAdapter(ingredientsAdapter);
        recyclerViewCategory.setAdapter(categoryAdapter);
        recyclerViewArea.setAdapter(areaAdapter);

        // Initialize presenter and load products
        searchPresenter = new SearchPresenter(this,repo);
        searchPresenter.getAllCategory();
        searchPresenter.getAllArea();
        searchPresenter.getAllIngredients();


    }

    private void initUI(View view) {
        recyclerViewIngredients = view.findViewById(R.id.first_recycler_view);
        recyclerViewCategory = view.findViewById(R.id.second_recycler_view);
        recyclerViewArea = view.findViewById(R.id.third_recycler_view);
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
