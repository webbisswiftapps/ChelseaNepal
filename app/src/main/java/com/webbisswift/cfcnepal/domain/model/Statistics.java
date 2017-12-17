
package com.webbisswift.cfcnepal.domain.model;


import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("views")
    private String mViews;

    public String getViews() {
        return mViews;
    }

    public void setViews(String views) {
        mViews = views;
    }

}
