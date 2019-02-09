package com.suzanelsamahy.linkdevelopment.api;

public interface ApiCallback {
    void onNewsDataRetrieveSuccess(Object object);
    void onFailure(String message);
}
