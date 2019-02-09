package com.suzanelsamahy.linkdevelopment.view;

import com.suzanelsamahy.linkdevelopment.models.NewsResponse;

public interface INewsView {
        void onNewsDataRetrieved(NewsResponse weather);
        void onNewsDataRetreieveError(String Message);
}
