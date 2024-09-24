package com.example.rosyrecipebox.network.ListOfMeals;

import android.util.Log;

import com.example.rosyrecipebox.network.Category.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private MealsService mealsService;
    private static MealsRemoteDataSourceImpl client = null;
    private String TAG="MealssNetwork";
    private MealsRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealsService = retrofit.create(MealsService.class);
    }

    public static MealsRemoteDataSourceImpl getInstance(){
        if(client == null)
        {
            client = new MealsRemoteDataSourceImpl();
        }
        return client;
    }

    public void makeNetworkCall(MealsNetworkCallback networkCallback, String dataType, String filterValue) {
        Call<MealsResponse> call;
        switch (dataType) {
            case "ingredient":
                call = mealsService.getMealsByIngredient(filterValue);
                break;
            case "category":
                call = mealsService.getMealsByCategory(filterValue);
                break;
            case "area":
                call = mealsService.getMealsByArea(filterValue);
                break;
            case "byId":
                call= mealsService.getMealById(filterValue);
                break;
            case "random":
                call= mealsService.getRandomMeal();
                break;
            default:
                networkCallback.onFailureResult("Unsupported data type: " + dataType);
                return;
        }

        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    networkCallback.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: "+response.body().meals.get(0).idMeal);
                } else {
                    networkCallback.onFailureResult("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

}
