package com.example.rosyrecipebox.network.Area;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private AreaService areaService;
    private static AreaRemoteDataSourceImpl client = null;
    private String TAG ="AreaNetwok";
    private AreaRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        areaService = retrofit.create(AreaService.class);
    }

    public static AreaRemoteDataSourceImpl getInstance(){
        if(client == null)
        {
            client = new AreaRemoteDataSourceImpl();
        }
        return client;
    }

    public void makeNetworkCall(AreaNetworkCallback networkCallback) {
        areaService.getAllAreas().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if(response.isSuccessful()){
                    networkCallback.onSuccessResultArea(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0));
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
                Log.i(TAG, "onFailure: ");
            }
        });
    }
}
