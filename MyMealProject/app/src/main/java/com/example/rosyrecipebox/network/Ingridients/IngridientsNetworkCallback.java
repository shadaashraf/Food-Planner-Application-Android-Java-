package com.example.rosyrecipebox.network.Ingridients;



import com.example.rosyrecipebox.model.Ingridients;

import java.util.List;

public interface IngridientsNetworkCallback {
    public void onSuccessResult(List<Ingridients> ingridients);
    public void onFailureResult(String errorMsg);
}
