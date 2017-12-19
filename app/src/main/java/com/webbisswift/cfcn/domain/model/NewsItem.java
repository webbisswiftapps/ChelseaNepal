
package com.webbisswift.cfcn.domain.model;

import com.google.gson.annotations.SerializedName;
import com.webbisswift.cfcn.utils.Utilities;

import org.joda.time.DateTime;


public class NewsItem {

    @SerializedName("content")
    private Content mContent;
    @SerializedName("enclosure")
    private Enclosure mEnclosure;
    @SerializedName("link")
    private String mLink;
    @SerializedName("pubDate")
    private String mPubDate;

    private DateTime pubDateTime;

    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("title")
    private String mTitle;

    public Content getContent() {
        return mContent;
    }

    public Enclosure getEnclosure() {
        return mEnclosure;
    }

    public String getLink() {
        return mLink;
    }


    public Thumbnail getThumbnail() {
        return mThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPubDateString(){
        return this.mPubDate;
    }
   public DateTime getPubDate(){

       if(pubDateTime == null) {
           //"Wednesday,  6 December 2017 1:30"
           //Thu, 07 Dec 2017 18:08:29 +0000

           try {
               this.pubDateTime= Utilities.parseRFC822Date(mPubDate);
           } catch (Exception e) {
               e.printStackTrace();
               this.pubDateTime = null;
           }
       }

       return pubDateTime;
   }


   public String getNewsAuthor(){
       if(mLink!=null){

           if(mLink.contains("guardian")){
               return "Guardian";
           }else if(mLink.contains("metro")){
               return "Metro";
           }else if(mLink.contains("dailymail")){
               return "Daily Mail";
           }else return "Chelsea News";

       }else return "Chelsea News";
   }


   public boolean hasVideo(){
       return (mEnclosure != null && mEnclosure.getType().contains("video"));
   }


   public String getThumbURL(){
       if(this.mThumbnail != null){
           return mThumbnail.getUrl();
       }else if(this.mEnclosure != null && this.mEnclosure.getType().contains("image")){
           return mEnclosure.getUrl();
       }else if(this.mContent != null){
           return mContent.getUrl();
       }else return null;
   }



}
