
package com.webbisswift.cfcnepal.domain.model;


import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoItem {

    @SerializedName("author")
    private Author mAuthor;
    @SerializedName("channelId")
    private String mChannelId;
    @SerializedName("group")
    private Group mGroup;
    @SerializedName("id")
    private String mId;
    @SerializedName("link")
    private Link mLink;
    @SerializedName("published")
    private String mPublished;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("updated")
    private String mUpdated;
    @SerializedName("videoId")
    private String mVideoId;

    DateTime pubDateTime;

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(String channelId) {
        mChannelId = channelId;
    }

    public Group getGroup() {
        return mGroup;
    }

    public void setGroup(Group group) {
        mGroup = group;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Link getLink() {
        return mLink;
    }

    public void setLink(Link link) {
        mLink = link;
    }

    public String getPublished() {
        return mPublished;
    }

    public void setPublished(String published) {
        mPublished = published;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String videoId) {
        mVideoId = videoId;
    }

    public DateTime getPubDate(){

        if(pubDateTime == null) {
            if(pubDateTime == null) {
                //"Wednesday,  6 December 2017 1:30"
                //Thu, 07 Dec 2017 18:08:29 +00:00

                try {
                    this.pubDateTime= new DateTime(mPublished);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.pubDateTime = null;
                }
            }

            return pubDateTime;
        }

        return pubDateTime;
    }

}
