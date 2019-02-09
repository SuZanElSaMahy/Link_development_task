package com.suzanelsamahy.linkdevelopment.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;


    public NewsResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.source);
        dest.writeString(this.sortBy);
        dest.writeTypedList(this.articles);
    }

    protected NewsResponse(Parcel in) {
        this.status = in.readString();
        this.source = in.readString();
        this.sortBy = in.readString();
        this.articles = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Parcelable.Creator<NewsResponse> CREATOR = new Parcelable.Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel source) {
            return new NewsResponse(source);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };
}
