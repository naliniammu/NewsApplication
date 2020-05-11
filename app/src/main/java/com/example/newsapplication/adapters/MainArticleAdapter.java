package com.example.newsapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapplication.Article;
import com.example.newsapplication.R;

import java.util.List;

public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.ViewHolder> {
    private List<Article> articleArrayList;
    private Context context;
    private OnItemclickListner onItemclickListner;
    public MainArticleAdapter(FragmentActivity activity, List<Article> articleArrayList, OnItemclickListner onItemclickListner) {
        this.context = context;
        this.articleArrayList = articleArrayList;
        this.onItemclickListner = this.onItemclickListner;
    }
    public interface OnItemclickListner {
        void onItemClick(int status, int position);
    }
    @Override
    public MainArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_main_article_adapter, viewGroup, false);
        return new MainArticleAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MainArticleAdapter.ViewHolder viewHolder, final int position) {
        final Article articleModel = articleArrayList.get(position);
        if(!TextUtils.isEmpty(articleModel.getTitle())) {
            viewHolder.titleText.setText(articleModel.getTitle());
        }
        if(!TextUtils.isEmpty(articleModel.getDescription())) {
            viewHolder.descriptionText.setText(articleModel.getDescription());
        }
        if (articleModel.getUrlToImage() != null) {
            Glide.with(context)
                    .load(articleModel.getUrlToImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(viewHolder.newsImage);
        } else {
            viewHolder.newsImage.setImageDrawable(null);
        }
        viewHolder.artilceAdapterParentLinear.setTag(articleModel);
        viewHolder.artilceAdapterParentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemclickListner != null) {
                    onItemclickListner.onItemClick(1, position);

                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView descriptionText;
        private RelativeLayout artilceAdapterParentLinear;
        private ImageView newsImage;

        ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.article_adapter_tv_title);
            descriptionText = view.findViewById(R.id.article_adapter_tv_description);
            artilceAdapterParentLinear = view.findViewById(R.id.article_adapter_ll_parent);
            newsImage = view.findViewById(R.id.news_image);
        }
    }
}
