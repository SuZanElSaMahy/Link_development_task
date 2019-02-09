package com.suzanelsamahy.linkdevelopment.api;

import com.suzanelsamahy.linkdevelopment.models.NewsResponse;
import com.suzanelsamahy.linkdevelopment.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static NewsAPI newsApi = null;

    private static ApiClient apiClient;

    public static synchronized ApiClient getInstance() {

        if (apiClient == null) {
            apiClient = new ApiClient();
            return apiClient;
        } else {
            return apiClient;
        }
    }

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApi = retrofit.create(NewsAPI.class);
    }

    public void getNewsResponse(String source, String apikey, final ApiCallback apiCallBack) {

        Callback<NewsResponse> responseCallback = new Callback<NewsResponse>() {
            @Override

            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.body() != null) {
                    apiCallBack.onNewsDataRetrieveSuccess(response.body());
                } else {
                    apiCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                t.printStackTrace();
                t.getCause();
                apiCallBack.onFailure(t.getMessage());
//                Log.d("api",""+t.getMessage());
            }
        };
        Call<NewsResponse> getResponseCall = newsApi.callNewsApi(source,apikey);
        getResponseCall.enqueue(responseCallback);
    }

}
