package com.webbisswift.cfcn.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apple on 12/8/17.
 */

public class VideoResults {

    @SerializedName("entry")
    private List<VideoItem> mItem;

    public List<VideoItem> getItem() {
        return mItem;
    }


}
