
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;

public class StarRating {

    @SerializedName("average")
    private String mAverage;
    @SerializedName("count")
    private String mCount;
    @SerializedName("max")
    private String mMax;
    @SerializedName("min")
    private String mMin;

    public String getAverage() {
        return mAverage;
    }

    public void setAverage(String average) {
        mAverage = average;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getMax() {
        return mMax;
    }

    public void setMax(String max) {
        mMax = max;
    }

    public String getMin() {
        return mMin;
    }

    public void setMin(String min) {
        mMin = min;
    }

}
