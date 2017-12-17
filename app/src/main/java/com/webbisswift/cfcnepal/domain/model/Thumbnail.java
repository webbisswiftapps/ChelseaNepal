
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;


public class Thumbnail {

    @SerializedName("height")
    private String mHeight;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("width")
    private String mWidth;

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getWidth() {
        return mWidth;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

}
