package com.suzanelsamahy.linkdevelopment.presenter;

import com.suzanelsamahy.linkdevelopment.api.ApiCallback;
import com.suzanelsamahy.linkdevelopment.api.ApiClient;
import com.suzanelsamahy.linkdevelopment.models.NewsResponse;
import com.suzanelsamahy.linkdevelopment.view.INewsView;

public class NewsPresenter implements INewsPresenter,ApiCallback {

    private INewsView newsView;

    public NewsPresenter(INewsView newsView){
        this.newsView = newsView;
    }

    @Override
    public void getNewsData(String apikey, String source) {
        ApiClient.getInstance().getNewsResponse(source,apikey,this);
    }

    @Override

    public void onDestroy() {
        newsView = null;
    }


    @Override
    public void onNewsDataRetrieveSuccess(Object object) {

        if (newsView == null) {
            return;
        }

        if (object != null && object instanceof NewsResponse) {
            NewsResponse response = (NewsResponse) object;
            if (response.getArticles() != null) {
                newsView.onNewsDataRetrieved(response);
            } else {
                newsView.onNewsDataRetreieveError(response.getStatus());
            }
        } else {
            newsView.onNewsDataRetreieveError("Error no data to retrieve");
        }
    }

    @Override
    public void onFailure(String message) {
        if(newsView != null){
            newsView.onNewsDataRetreieveError(message);
        }
    }
}
