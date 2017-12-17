
package com.webbisswift.cfcnepal.domain.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Results {

    @SerializedName("item")
    private List<NewsItem> mItem;

    public List<NewsItem> getItem() {
        return mItem;
    }



}
