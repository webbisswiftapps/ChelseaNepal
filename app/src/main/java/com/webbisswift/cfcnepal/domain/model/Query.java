
package com.webbisswift.cfcnepal.domain.model;

import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("results")
    private Results mResults;

    public Long getCount() {
        return mCount;
    }

    public String getCreated() {
        return mCreated;
    }

    public String getLang() {
        return mLang;
    }

    public Results getResults() {
        return mResults;
    }

    public static class Builder {

        private Long mCount;
        private String mCreated;
        private String mLang;
        private Results mResults;

        public Query.Builder withCount(Long count) {
            mCount = count;
            return this;
        }

        public Query.Builder withCreated(String created) {
            mCreated = created;
            return this;
        }

        public Query.Builder withLang(String lang) {
            mLang = lang;
            return this;
        }

        public Query.Builder withResults(Results results) {
            mResults = results;
            return this;
        }

        public Query build() {
            Query Query = new Query();
            Query.mCount = mCount;
            Query.mCreated = mCreated;
            Query.mLang = mLang;
            Query.mResults = mResults;
            return Query;
        }

    }

}
