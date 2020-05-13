package com.example.newsapplication.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapplication.APIInterface;
import com.example.newsapplication.ApiClient;
import com.example.newsapplication.Article;
import com.example.newsapplication.R;
import com.example.newsapplication.ResponseModel;
import com.example.newsapplication.WebActivity;
import com.example.newsapplication.adapters.MainArticleAdapter;
import com.example.newsapplication.databinding.LatestNewsFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsapplication.Constants.API_KEY;
import static com.example.newsapplication.Constants.business;
import static com.example.newsapplication.Constants.source;
import static java.util.Objects.*;

public class BusinessFragment extends Fragment implements  MainArticleAdapter.OnItemclickListner {
    private LatestNewsFragmentBinding latestNewsFragmentBinding;
    private MainArticleAdapter mainArticleAdapter;
    private MainArticleAdapter.OnItemclickListner onItemclickListner;
    private List<Article> articleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        latestNewsFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.latest_news_fragment, container, false);
        View view = latestNewsFragmentBinding.getRoot();
        onItemclickListner = this;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        latestNewsFragmentBinding.newsListRecyclerview.setLayoutManager(mLayoutManager);
        loadNewsdata();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void loadNewsdata() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiService.getLatestNews(business, API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("ok")) {
                    articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        mainArticleAdapter = new MainArticleAdapter(getActivity(), articleList, onItemclickListner);
                        latestNewsFragmentBinding.newsListRecyclerview.setAdapter(mainArticleAdapter);

                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }
    @Override
    public void onItemClick(int status, int position) {
        if (status == 1) {
            String articlesURL = articleList.get(position).getUrl();
            if (!TextUtils.isEmpty(articlesURL)) {
                Intent webActivity = new Intent(getActivity(), WebActivity.class);
                webActivity.putExtra("url", articlesURL);
                startActivity(webActivity);
            }

        }
    }
}
