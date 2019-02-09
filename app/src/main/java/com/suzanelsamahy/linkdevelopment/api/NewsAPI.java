package com.suzanelsamahy.linkdevelopment.api;

import com.suzanelsamahy.linkdevelopment.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    //https://newsapi.org/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9
    @GET("v1/articles")
    Call<NewsResponse> callNewsApi( @Query("source") String source,
                                    @Query("apiKey") String apiKey);
}
