package com.webbisswift.cfcn.domain.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 12/8/17.
 */

public class VideoResponse {

    @SerializedName("query")
    private VideoQuery mQuery;

    public VideoQuery getQuery() {
        return mQuery;
    }

    public static VideoResponse parseResponse(String responseJson){
        Gson gson = new GsonBuilder().create();
        VideoResponse response = gson.fromJson(responseJson, VideoResponse.class);
        return response;
    }
}
