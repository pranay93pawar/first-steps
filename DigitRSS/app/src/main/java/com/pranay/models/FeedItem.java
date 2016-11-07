package com.pranay.models;

/**
 * Created by pranay on 24/09/16.
 */

public class FeedItem {

    private String title;
    private String navigationLink;
    private String imageLink;
    private String description;
    private String pubDate;
    private String postAuthor;

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
