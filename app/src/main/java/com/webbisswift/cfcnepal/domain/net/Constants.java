package com.webbisswift.cfcnepal.domain.net;

/**
 * Created by apple on 12/8/17.
 */

public class Constants {

    public static final String CNEWS_FEED_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20title%2C%20link%2C%20pubDate%2C%20content%2C%20thumbnail%2C%20enclosure%20from%20rss%20where%20url%20in%20('http%3A%2F%2Fwww.chelsea-news.co%2Ffeed%2F')%7C%20unique(field%3D%22channel.link%22)%20&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String METRO_FEED_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20title%2C%20pubDate%2C%20thumbnail%2C%20enclosure%2C%20content%2Clink%20from%20rss%20where%20url%20in%20(%20%20%20'http%3A%2F%2Fmetro.co.uk%2Ftag%2Fchelsea-fc%2Ffeed%2F'%20)%20%7C%20unique(field%3D%22link%22%2C%20hideRepeatCount%3D%22true%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String GUARDIAN_FEED_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20title%2C%20link%2C%20media%3Acontent.url%2C%20pubDate%20from%20rss%20where%20url%20in%20('https%3A%2F%2Fwww.theguardian.com%2Ffootball%2Fchelsea%2Frss')%7Cunique(field%3D'link')&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String DM_FEED_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20title%2C%20link%2C%20pubDate%2C%20content%2C%20thumbnail%2C%20enclosure%20from%20rss%20where%20url%20in%20('http%3A%2F%2Fwww.dailymail.co.uk%2Fsport%2Fteampages%2Fchelsea.rss')%7C%20unique(field%3D%22channel.link%22)%20&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    //chelsea videos url
    public static final String SHPENDI_CFC_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20feed%20where%20url%20in%20('https%3A%2F%2Fwww.youtube.com%2Ffeeds%2Fvideos.xml%3Fchannel_id%3DUC6IRUel01QXtLC_Kj6latlw')%20LIMIT%205&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String CHELSEA_FC_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20feed%20where%20url%20in%20('https%3A%2F%2Fwww.youtube.com%2Ffeeds%2Fvideos.xml%3Fchannel_id%3DUCU2PacFf99vhb3hNiYDmxww')%20LIMIT%205&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String H100_PER_CHE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20feed%20where%20url%20in%20('https%3A%2F%2Fwww.youtube.com%2Ffeeds%2Fvideos.xml%3Fchannel_id%3DUCb51e53V1Cq3jx8_EFYflWA')%20LIMIT%205&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    public static final String BEANYMAN_CHE_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20feed%20where%20url%3D'https%3A%2F%2Fwww.youtube.com%2Ffeeds%2Fvideos.xml%3Fchannel_id%3DUCiVg6vRhuyjsWgHkDNOig6A'%20AND%20title%20like%20'%25chelsea%25'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";


}

