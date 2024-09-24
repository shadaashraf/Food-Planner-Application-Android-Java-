package com.example.rosyrecipebox.model;

import androidx.lifecycle.LiveData;

import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.network.Area.AreaNetworkCallback;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryNetworkCallback;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsNetworkCallback;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsNetworkCallback;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;

import java.util.List;

public class MealsRepositoryImpl {
    AreaRemoteDataSourceImpl ArearemoteSource;
    CategoryRemoteDataSourceImpl CategoryremoteSource;
    IngridientsRemoteDataSourceImpl IngredientsremoteSource;
    MealsRemoteDataSourceImpl MealsremoteSource;
    MealLocalDataSourceImpl localSource;
    private static MealsRepositoryImpl repo = null;

    public MealsRepositoryImpl(AreaRemoteDataSourceImpl _ArearemoteSource,MealsRemoteDataSourceImpl _MealsremoteSource,IngridientsRemoteDataSourceImpl _IngredientsremoteSource,CategoryRemoteDataSourceImpl _CategoryremoteSource, MealLocalDataSourceImpl _localSource)
    {
        ArearemoteSource = _ArearemoteSource;
        CategoryremoteSource=_CategoryremoteSource;
        IngredientsremoteSource=_IngredientsremoteSource;
        MealsremoteSource=_MealsremoteSource;
        localSource = _localSource;
    }

    public static MealsRepositoryImpl getInstance(AreaRemoteDataSourceImpl _ArearemoteSource,MealsRemoteDataSourceImpl _MealsremoteSource,IngridientsRemoteDataSourceImpl _IngredientsremoteSource,CategoryRemoteDataSourceImpl _CategoryremoteSource, MealLocalDataSourceImpl _localSource)
    {
        if(repo == null)
        {
            repo = new MealsRepositoryImpl(_ArearemoteSource,_MealsremoteSource,_IngredientsremoteSource,_CategoryremoteSource, _localSource);
        }
        return repo;
    }

    public LiveData<List<Meal>> getStoredProducts()
    {
        return localSource.getStoredData();
    }

    public void getAllAreas(AreaNetworkCallback networkCallback)
    {
        ArearemoteSource.makeNetworkCall(networkCallback);
    }
    public void getAllCategory(CategoryNetworkCallback networkCallback)
    {
        CategoryremoteSource.makeNetworkCall(networkCallback);
    }
    public void getAllIngredients(IngridientsNetworkCallback networkCallback)
    {
        IngredientsremoteSource.makeNetworkCall(networkCallback);
    }
    public void getAllMeals(MealsNetworkCallback networkCallback,String dataTYpe,String filterValue)
    {
        MealsremoteSource.makeNetworkCall(networkCallback,dataTYpe,filterValue);
    }


    public void insertProduct(Meal meal)
    {
        localSource.insert(meal);
    }

    public void deleteProduct(Meal meal)
    {
        localSource.delete(meal);
    }
}
