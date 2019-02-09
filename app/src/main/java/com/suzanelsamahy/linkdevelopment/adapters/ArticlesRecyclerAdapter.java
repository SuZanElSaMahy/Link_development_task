package com.suzanelsamahy.linkdevelopment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suzanelsamahy.linkdevelopment.R;
import com.suzanelsamahy.linkdevelopment.models.Article;
import com.suzanelsamahy.linkdevelopment.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Article> newsList;
    private final OnItemClickListener listener;

    public ArticlesRecyclerAdapter(List<Article> newsList, Context context, OnItemClickListener clickListener ) {
        this.newsList = newsList;
        this.context = context;
        listener = clickListener;
    }

    @NonNull
    @Override
    public ArticlesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.article_recycler_item, viewGroup, false);
        return new ArticlesRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesRecyclerAdapter.ViewHolder viewHolder, int i) {
        final Article articleItem = newsList.get(i);
        viewHolder.titleTv.setText(articleItem.getTitle());
        viewHolder.subTitleTv.setText("By "+articleItem.getAuthor());

        viewHolder.dateTv.setText(Utilities.parsePublishedDate(articleItem.getPublishedAt()));
        if (articleItem.getUrlToImage() != null) {
            Glide.with(context).load(articleItem.getUrlToImage()).into(viewHolder.newsIv);
        } else {
            Glide.with(context).load(R.drawable.ic_placeholder).into(viewHolder.newsIv);
        }

        viewHolder.articleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int pos = (int) v.getTag();
                listener.onItemClick(articleItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_title)
        TextView titleTv;

        @BindView(R.id.article_subtitle)
        TextView subTitleTv;

        @BindView(R.id.date_tv)
        TextView dateTv;

        @BindView(R.id.news_iv)
        ImageView newsIv;

        @BindView(R.id.article_layout)
        RelativeLayout articleLayout;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateAdapter(ArrayList<Article> items) {
        newsList = new ArrayList<>();
        newsList.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Article pos);
    }

}
