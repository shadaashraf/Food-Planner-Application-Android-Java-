package com.example.rosyrecipebox.network.ListOfMeals;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {
    @GET("filter.php")
    Call<MealsResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String Category);

    @GET("filter.php")
    Call<MealsResponse> getMealsByArea(@Query("a") String Area);
    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") String MealID);
    @GET("random.php")
    Call<MealsResponse> getRandomMeal();
}
