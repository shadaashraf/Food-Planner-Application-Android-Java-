package com.example.rosyrecipebox.network.Category;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private CategoryService categoryService;
    private static CategoryRemoteDataSourceImpl client = null;
    private String TAG="CategoryNetwork";
    private CategoryRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        categoryService = retrofit.create(CategoryService.class);
    }

    public static CategoryRemoteDataSourceImpl getInstance(){
        if(client == null)
        {
            client = new CategoryRemoteDataSourceImpl();
        }
        return client;
    }

    public void makeNetworkCall(CategoryNetworkCallback networkCallback) {
        categoryService.getAlCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    networkCallback.onSuccessResultCategory(response.body().meals);
                    Log.i(TAG, "onResponse: "+response.body().meals.get(0));
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }
}
