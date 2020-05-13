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

import com.example.newsapplication.Article;
import com.example.newsapplication.R;
import com.example.newsapplication.WebActivity;
import com.example.newsapplication.adapters.MainArticleAdapter;
import com.example.newsapplication.databinding.LatestNewsFragmentBinding;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.newsapplication.Constants.API_KEY;
import static com.example.newsapplication.Constants.business;
import static com.example.newsapplication.Constants.source;
import static java.util.Objects.*;

public class BusinessFragment extends Fragment implements  MainArticleAdapter.OnItemclickListner {
    private LatestNewsFragmentBinding latestNewsFragmentBinding;
    private MainArticleAdapter mainArticleAdapter;
    private MainArticleAdapter.OnItemclickListner onItemclickListner;
    private LatestNewsViewModel mViewModel;
    private ArrayList<Article> articleArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        latestNewsFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.latest_news_fragment, container, false);
        View view = latestNewsFragmentBinding.getRoot();
        onItemclickListner = this;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        latestNewsFragmentBinding.newsListRecyclerview.setLayoutManager(mLayoutManager);
        mViewModel = ViewModelProviders.of(this).get(LatestNewsViewModel.class);
      //  fetchDetails();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void fetchDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        observeResturantInfo();
        mViewModel.fetchNewsInfo(business, API_KEY);
        progressDialog.dismiss();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void observeResturantInfo() {
        mViewModel.getNews().observe(requireNonNull(getActivity()),
                new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> data) {
                mainArticleAdapter = new MainArticleAdapter(getActivity(), data, onItemclickListner);
                latestNewsFragmentBinding.newsListRecyclerview.setAdapter(mainArticleAdapter);
                mainArticleAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onItemClick(int status, int position) {
        if (status == 1) {
            String articlesURL = articleArrayList.get(position).getUrl();
            if (!TextUtils.isEmpty(articlesURL)) {
                Intent webActivity = new Intent(getActivity(), WebActivity.class);
                webActivity.putExtra("url", articlesURL);
                startActivity(webActivity);
            }

        }
    }
}
