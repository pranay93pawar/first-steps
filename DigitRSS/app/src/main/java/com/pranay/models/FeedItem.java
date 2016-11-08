package com.pranay.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pranay on 24/09/16.
 */

public class FeedItem implements Parcelable {

    private String title;
    private String navigationLink;
    private String imageLink;
    private String description;
    private String pubDate;
    private String postAuthor;

    public FeedItem(){

    }
    public FeedItem(Parcel in) {
        title = in.readString();
        navigationLink = in.readString();
        imageLink = in.readString();
        description = in.readString();
        pubDate = in.readString();
        postAuthor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(navigationLink);
        dest.writeString(imageLink);
        dest.writeString(description);
        dest.writeString(pubDate);
        dest.writeString(postAuthor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel in) {
            return new FeedItem(in);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNavigationLink() {
        return navigationLink;
    }

    public void setNavigationLink(String navigationLink) {
        this.navigationLink = navigationLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }
}
