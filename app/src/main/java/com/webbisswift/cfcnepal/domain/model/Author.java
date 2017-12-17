
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("name")
    private String mName;
    @SerializedName("uri")
    private String mUri;

    public String getName() {
        return mName+" | Youtube";
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

}
