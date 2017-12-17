package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 12/8/17.
 */

public class VideoQuery {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("results")
    private VideoResults mResults;

    public Long getCount() {
        return mCount;
    }

    public String getCreated() {
        return mCreated;
    }

    public String getLang() {
        return mLang;
    }

    public VideoResults getResults() {
        return mResults;
    }

    public static class Builder {

        private Long mCount;
        private String mCreated;
        private String mLang;
        private Results mResults;


    }
}
