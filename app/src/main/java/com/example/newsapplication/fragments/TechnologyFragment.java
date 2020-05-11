package com.example.newsapplication.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapplication.Article;
import com.example.newsapplication.R;
import com.example.newsapplication.adapters.MainArticleAdapter;
import com.example.newsapplication.databinding.LatestNewsFragmentBinding;

import java.util.ArrayList;


public class TechnologyFragment extends Fragment  implements MainArticleAdapter.OnItemclickListner {
    private LatestNewsFragmentBinding latestNewsFragmentBinding;
    private MainArticleAdapter mainArticleAdapter;
    private MainArticleAdapter.OnItemclickListner onItemclickListner;
    private LatestNewsViewModel mViewModel;
    private ArrayList<Article> articleArrayList = new ArrayList<Article>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        latestNewsFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.latest_news_fragment, container, false);
        View view = latestNewsFragmentBinding.getRoot();
        onItemclickListner = this;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        latestNewsFragmentBinding.newsListRecyclerview.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onItemClick(int status, int position) {

    }
}
