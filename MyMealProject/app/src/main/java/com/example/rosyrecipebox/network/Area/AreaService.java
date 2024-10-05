package com.example.rosyrecipebox.network.Area;


import retrofit2.Call;
import retrofit2.http.GET;

public interface AreaService {
    @GET("list.php?a=list")
    Call<AreaResponse> getAllAreas();
}