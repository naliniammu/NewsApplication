package com.example.newsapplication.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapplication.APIInterface;
import com.example.newsapplication.ApiClient;
import com.example.newsapplication.Article;
import com.example.newsapplication.ResponseModel;
import com.example.newsapplication.adapters.MainArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsapplication.Constants.API_KEY;
import static com.example.newsapplication.Constants.source;

public class LatestNewsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Article>> arrayListMutableLiveData;


    public LiveData<ArrayList<Article>> getNews() {
        if (arrayListMutableLiveData == null) {
            arrayListMutableLiveData = new MutableLiveData<ArrayList<Article>>();
        }
        return arrayListMutableLiveData;
    }

    public void fetchNewsInfo(String source, String api_key) {
        loadResturantata(source, api_key);
    }

    private void loadResturantata(String source, String api_key) {
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiService.getLatestNews(source, api_key);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
