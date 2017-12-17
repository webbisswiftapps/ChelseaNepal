
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;

public class Community {

    @SerializedName("starRating")
    private StarRating mStarRating;
    @SerializedName("statistics")
    private Statistics mStatistics;

    public StarRating getStarRating() {
        return mStarRating;
    }

    public void setStarRating(StarRating starRating) {
        mStarRating = starRating;
    }

    public Statistics getStatistics() {
        return mStatistics;
    }

    public void setStatistics(Statistics statistics) {
        mStatistics = statistics;
    }

}
