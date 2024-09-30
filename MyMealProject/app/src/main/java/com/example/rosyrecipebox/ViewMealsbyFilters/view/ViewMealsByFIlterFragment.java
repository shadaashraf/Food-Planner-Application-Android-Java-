package com.example.rosyrecipebox.ViewMealsbyFilters.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.MealDetails.view.MealDetailFragment;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.ViewMealsbyFilters.presenter.ViewMealsByFIlterPresenter;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;
import com.example.rosyrecipebox.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class ViewMealsByFIlterFragment extends Fragment implements ViewMealsByFIlterViewInterface, ViewAllOnclickListener {


    private ViewMealsByFIlterPresenter viewMealsByFIlterPresenter;
    private ViewMealsByFIlterAdapter viewMealsByFIlterAdapter;
    private RecyclerView recyclerView;
    private Button areaBtn;
    private Button ingredientsBtn;
    private Button categoryBtn;
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

        dataValue = null;

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance(AreaRemoteDataSourceImpl.getInstance(),
                MealsRemoteDataSourceImpl.getInstance(),
                IngridientsRemoteDataSourceImpl.getInstance(),
                CategoryRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext()));

        initUI(view);



        // Initialize presenter
        viewMealsByFIlterPresenter = new ViewMealsByFIlterPresenter(this, repo);
        viewMealsByFIlterPresenter.getProducts(dataValue,filtrtValue);

        mytext.setText(dataValue+" Meals");

        viewMealsByFIlterAdapter = new ViewMealsByFIlterAdapter(getContext(), new ArrayList<>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // Span count = 2 for 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(viewMealsByFIlterAdapter);
    }


    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.ViewDataRecycle);
         mytext=view.findViewById(R.id.AllCountries);
    }

    @Override
    public void ShowData(List<Meal> meals) {
        if(viewMealsByFIlterAdapter.ById){
            MealDetailFragment mealDetailFragment = new MealDetailFragment(meals.get(0));

            // Navigate to the new Fragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, mealDetailFragment); // Replace with your FragmentContainerView ID
            transaction.addToBackStack(null); // Optional: add to back stack
            transaction.commit();
        }
        else {
            viewMealsByFIlterAdapter.setList(meals);
            viewMealsByFIlterAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show(); // Show error messages
    }

    @Override
    public void SaveMeal(Meal meal) {
        viewMealsByFIlterPresenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeleteMeal(Meal meal) {
        viewMealsByFIlterPresenter.removeFromSaved(meal);
        Toast.makeText(getContext(), "Meal Removed From Favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OpenDetails(Meal meal) {

        viewMealsByFIlterPresenter.getProducts("byId",meal.idMeal);

    }



}
