
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

    @SerializedName("featuredImage")
    private String featuredImage;

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
           }else if(mLink.contains("squawka")){
               return "Squawka";
           }else if(mLink.contains("football365")){
                return "Football 365";
           }else return "Chelsea News";

       }else return "Chelsea News";
   }


   public boolean hasVideo(){
       return (mEnclosure != null && mEnclosure.getType().contains("video"));
   }


   public String getThumbURL(){
       if(this.mThumbnail != null){
           return mThumbnail.getUrl();
       }else if(this.featuredImage != null && !featuredImage.isEmpty()){
           return featuredImage;
       }else if(this.mEnclosure != null){
           if(this.mEnclosure.getType().contains("image") || mEnclosure.getType().contains("Image")) {
               return mEnclosure.getUrl();
           }else return "http://m.gardensbythebay.com.sg/etc/designs/gbb/clientlibs/images/common/not_found.jpg";
       }else if(this.mContent != null){
           return mContent.getUrl();
       }else return  "http://m.gardensbythebay.com.sg/etc/designs/gbb/clientlibs/images/common/not_found.jpg";
   }



}
