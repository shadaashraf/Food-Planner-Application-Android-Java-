package com.example.rosyrecipebox.network.Category;



import com.example.rosyrecipebox.model.Category;

import java.util.List;

public interface CategoryNetworkCallback {
    public void onSuccessResult(List<Category> categories);
    public void onFailureResult(String errorMsg);
}
