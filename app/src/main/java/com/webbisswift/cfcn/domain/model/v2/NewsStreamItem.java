package com.webbisswift.cfcn.domain.model.v2;

import com.twitter.sdk.android.core.models.Coordinates;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.webbisswift.cfcn.utils.Utilities;

import org.joda.time.DateTime;

public class NewsStreamItem {


    public static int NEWS_ARTICLE = 0;
    public static int NEWS_ARTICLE_HIGHLIGHTED  = 1;
    public static int YOUTUBE_VIDEO = 2;
    public static int TWEET = 3;
    public static int DEFAULT = 4;
    public static int AD_SMALL = 5;
    public static int AD_LARGE = 6;



    String guid, author, image, link, summary, pubDate, type, title;
    boolean is_highlighted;

    public NewsStreamItem(){}

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public DateTime getPubDate(){
        try {
            return Utilities.parseNewsDate(this.pubDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIs_highlighted() {
        return is_highlighted;
    }

    public void setIs_highlighted(boolean is_highlighted) {
        this.is_highlighted = is_highlighted;
    }



    public int getItemTypeId(){

        if(getType().contentEquals("news_article")){ return isIs_highlighted() ? NEWS_ARTICLE_HIGHLIGHTED : NEWS_ARTICLE;
        }else if(getType().contentEquals("youtube_video")){
            return YOUTUBE_VIDEO;
        }else if(getType().contentEquals("tweet")){
            return TWEET;
        }else{
            return DEFAULT;
        }
    }


   public Long getTweetId(){
        try{
            Long tweetIdL = Long.parseLong(guid);
            return tweetIdL;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
   }



}
