
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;


public class Group {

    @SerializedName("community")
    private Community mCommunity;
    @SerializedName("content")
    private Content mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("title")
    private String mTitle;

    public Community getCommunity() {
        return mCommunity;
    }

    public void setCommunity(Community community) {
        mCommunity = community;
    }

    public Content getContent() {
        return mContent;
    }

    public void setContent(Content content) {
        mContent = content;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Thumbnail getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
