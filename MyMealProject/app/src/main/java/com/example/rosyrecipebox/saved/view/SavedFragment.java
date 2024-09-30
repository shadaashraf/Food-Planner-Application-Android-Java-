package com.example.rosyrecipebox.saved.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.MealDetails.view.MealDetailFragment;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;
import com.example.rosyrecipebox.saved.presenter.SavedPresenter;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment implements SaveOnclickListener, SavedViewInterface {

    private RecyclerView recyclerView;
    private SavedAdapter savedAdapter;
    private SavedPresenter savedPresenter;
    private LinearLayoutManager layoutManager;
    private MealsRepositoryImpl repo;
    MealsRemoteDataSourceImpl mealsRemoteDataSource;
    IngridientsRemoteDataSourceImpl ingridientsRemoteDataSource;
    CategoryRemoteDataSourceImpl categoryRemoteDataSource;
    AreaRemoteDataSourceImpl areaRemoteDataSource;
    MealLocalDataSourceImpl mealsRepository;

    // Override the onCreateView method to inflate the fragment's layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);

    }

    // Override onViewCreated to initialize UI elements after the view is created
    @SuppressLint("FragmentLiveDataObserve")
    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance( AreaRemoteDataSourceImpl.getInstance(), MealsRemoteDataSourceImpl.getInstance(), IngridientsRemoteDataSourceImpl.getInstance(), CategoryRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));

        // Initialize UI elements
        initUI(view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        // Set up adapter
        savedAdapter = new SavedAdapter(getContext(), new ArrayList<>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // Span count = 2 for 2 columns
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(savedAdapter);

        // Initialize presenter and load products
        savedPresenter = new SavedPresenter(SavedFragment.this, repo);
        LiveData<List<Meal>> storedMeals=savedPresenter.getProducts();
        storedMeals.observe(SavedFragment.this, new Observer<List<Meal>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Meal> products) {
                if (products != null) {
                    showData(products);
                }
                else {
                    showErrMsg("There are no products");
                }
            }
        });


    }

    // Method to initialize UI components
    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.AllFavRecyclerView);
    }

    // Method to display data when it is available
    @Override
    public void showData(List<Meal> meals ){
        savedAdapter.setList(meals);
        savedAdapter.notifyDataSetChanged();
    }


    // Method to show an error message
    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
    }

    // Method to save a meal (add to favorites)
    @Override
    public void SaveMeal(Meal meal) {
        savedPresenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();
    }

    // Method to delete a meal (remove from favorites)
    @Override
    public void DeleteMeal(Meal meal) {
        savedPresenter.removeFromSaved(meal);
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
}
