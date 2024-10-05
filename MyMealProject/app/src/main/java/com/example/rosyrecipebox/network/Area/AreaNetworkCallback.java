package com.example.rosyrecipebox.network.Area;



import com.example.rosyrecipebox.model.Area;

import java.util.List;

public interface AreaNetworkCallback {
    public void onSuccessResultArea(List<Area> areass);
    public void onFailureResult(String errorMsg);
}
