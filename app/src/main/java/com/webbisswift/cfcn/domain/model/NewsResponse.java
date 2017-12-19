
package com.webbisswift.cfcn.domain.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;


public class NewsResponse {

    @SerializedName("query")
    private Query mQuery;

    public Query getQuery() {
        return mQuery;
    }

    public static NewsResponse parseResponse(String responseJson){
        Gson gson = new GsonBuilder().create();
        NewsResponse response = gson.fromJson(responseJson, NewsResponse.class);
        return response;

    }

}
