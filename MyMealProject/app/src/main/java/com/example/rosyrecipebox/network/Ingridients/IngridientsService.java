package com.example.rosyrecipebox.network.Ingridients;

import com.example.rosyrecipebox.network.Category.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IngridientsService {
    @GET("list.php?i=list")
    Call<IngradiantsResponse> getAllIngradiants();
}
