
package com.webbisswift.cfcn.domain.model;


import com.google.gson.annotations.SerializedName;


public class Enclosure {

    @SerializedName("length")
    private String mLength;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;

    public String getLength() {
        return mLength;
    }

    public String getType() {
        return mType;
    }

    public String getUrl() {
        return mUrl;
    }

    public static class Builder {

        private String mLength;
        private String mType;
        private String mUrl;

        public Enclosure.Builder withLength(String length) {
            mLength = length;
            return this;
        }

        public Enclosure.Builder withType(String type) {
            mType = type;
            return this;
        }

        public Enclosure.Builder withUrl(String url) {
            mUrl = url;
            return this;
        }

        public Enclosure build() {
            Enclosure Enclosure = new Enclosure();
            Enclosure.mLength = mLength;
            Enclosure.mType = mType;
            Enclosure.mUrl = mUrl;
            return Enclosure;
        }

    }

}
