package com.example.rosyrecipebox.network.Category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("categories.php")
    Call<CategoryResponse> getAlCategory();
}
