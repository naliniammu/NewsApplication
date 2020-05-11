package com.example.newsapplication.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapplication.APIInterface;
import com.example.newsapplication.ApiClient;
import com.example.newsapplication.Article;
import com.example.newsapplication.MainActivity;
import com.example.newsapplication.R;
import com.example.newsapplication.ResponseModel;
import com.example.newsapplication.WebActivity;
import com.example.newsapplication.adapters.MainArticleAdapter;
import com.example.newsapplication.databinding.LatestNewsFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsapplication.Constants.API_KEY;
import static com.example.newsapplication.Constants.source;

public class LatestNewsFragment extends Fragment implements MainArticleAdapter.OnItemclickListner {
    private LatestNewsFragmentBinding latestNewsFragmentBinding;
    private MainArticleAdapter mainArticleAdapter;
    private MainArticleAdapter.OnItemclickListner onItemclickListner;
    private LatestNewsViewModel mViewModel;
    private ArrayList<Article> articleArrayList = new ArrayList<Article>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        latestNewsFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.latest_news_fragment, container, false);
        View view = latestNewsFragmentBinding.getRoot();
        onItemclickListner = this;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        latestNewsFragmentBinding.newsListRecyclerview.setLayoutManager(mLayoutManager);
        mViewModel = ViewModelProviders.of(this).get(LatestNewsViewModel.class);
        fetchDetails();
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
        mViewModel.fetchNewsInfo(source, API_KEY);
        progressDialog.dismiss();

    }


    public void observeResturantInfo() {
        mViewModel.getNews().observe(getActivity(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> data) {
                mainArticleAdapter = new MainArticleAdapter(LatestNewsFragment.this.getActivity(), data, onItemclickListner);
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
