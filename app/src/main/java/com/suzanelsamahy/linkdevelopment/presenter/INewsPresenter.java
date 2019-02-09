package com.suzanelsamahy.linkdevelopment.presenter;

public interface INewsPresenter {

    void getNewsData(String apikey, String source);
    void onDestroy();

}
