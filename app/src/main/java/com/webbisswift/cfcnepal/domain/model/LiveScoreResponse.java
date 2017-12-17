
package com.webbisswift.cfcnepal.domain.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class LiveScoreResponse {

    @SerializedName("matches")
    private List<LSMatch> mMatches;

    public List<LSMatch> getMatches() {
        return mMatches;
    }

    public void setMatches(List<LSMatch> matches) {
        mMatches = matches;
    }


    public static LiveScoreResponse parseResponse(String responseJson){
        Gson gson = new GsonBuilder().create();
        LiveScoreResponse response = gson.fromJson(responseJson, LiveScoreResponse.class);
        return response;

    }

}
