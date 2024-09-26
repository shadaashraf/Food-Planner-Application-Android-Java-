package com.example.rosyrecipebox.network.Ingridients;



import com.example.rosyrecipebox.model.Ingridients;

import java.util.List;

public interface IngridientsNetworkCallback {
    public void onSuccessResultIngrediants(List<Ingridients> ingridients);
    public void onFailureResult(String errorMsg);
}
