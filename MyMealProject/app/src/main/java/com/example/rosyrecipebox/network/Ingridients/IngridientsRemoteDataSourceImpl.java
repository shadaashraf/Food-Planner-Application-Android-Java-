package com.example.rosyrecipebox.network.Ingridients;

import com.example.rosyrecipebox.network.Category.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngridientsRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private IngridientsService ingridientsService;
    private static IngridientsRemoteDataSourceImpl client = null;

    private IngridientsRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ingridientsService = retrofit.create(IngridientsService.class);
    }

    public static IngridientsRemoteDataSourceImpl getInstance(){
        if(client == null)
        {
            client = new IngridientsRemoteDataSourceImpl();
        }
        return client;
    }

    public void makeNetworkCall(IngridientsNetworkCallback networkCallback) {
        ingridientsService.getAllIngradiants().enqueue(new Callback<IngradiantsResponse>() {
            @Override
            public void onResponse(Call<IngradiantsResponse> call, Response<IngradiantsResponse> response) {
                if(response.isSuccessful()){
                    networkCallback.onSuccessResultIngrediants(response.body().meals);
                }
            }

            @Override
            public void onFailure(Call<IngradiantsResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }
}
