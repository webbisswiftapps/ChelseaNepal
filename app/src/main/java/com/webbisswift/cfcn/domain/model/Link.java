
package com.webbisswift.cfcn.domain.model;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("href")
    private String mHref;
    @SerializedName("rel")
    private String mRel;

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getRel() {
        return mRel;
    }

    public void setRel(String rel) {
        mRel = rel;
    }

}
