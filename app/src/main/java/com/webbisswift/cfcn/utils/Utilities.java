package com.webbisswift.cfcn.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 11/30/17.
 */

public class Utilities {


    public static String getCopyrightDate(){
        try {
            DateTime now = new DateTime();
            return "© " + now.year().getAsText();
        }catch (Exception e){
            return "";
        }
    }



    public static long getTimeDifferenceFromNow(Date checkDate){
        Date now = new Date();
        return checkDate.getTime() - now.getTime();
    }

    public static DateTime parseRFC822Date(String rfc822){

        try{
            return new DateTime(rfc822);
        }catch (Exception e){
            // create parser for "GMT"
            DateTimeParser gmtParser = DateTimeFormat.forPattern("ZZZ").getParser();
            DateTimeParser offsetParser = DateTimeFormat.forPattern("Z").getParser();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("EEE, dd MMM yyyy HH:mm:ss ") // common pattern
                    .appendOptional(gmtParser)    // optional parser for GMT
                    .appendOptional(offsetParser) // optional parser for +0200
                    .toFormatter().withZoneUTC().withOffsetParsed();


            return formatter.parseDateTime(rfc822);
        }


    }

    public static DateTime parseNewsDate(String date){

        try{
            return new DateTime(date);
        }catch (Exception e) {
            // create parser for "GMT"
            DateTimeParser gmtParser = DateTimeFormat.forPattern("ZZZ").getParser();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("EEE, dd MMM yyyy HH:mm:ss ") // common pattern
                    .appendOptional(gmtParser)    // optional parser for GMT
                    .toFormatter().withZoneUTC().withOffsetParsed();

            return formatter.parseDateTime(date);
        }
    }



    public static String getTimeAgo(DateTime chk){
        DateTime now = new DateTime();
        Period period = new Period(chk, now);



        PeriodFormatterBuilder formatter = new PeriodFormatterBuilder();

        if(period.getYears() > 0){
            formatter.appendYears().appendSuffix(" y");
        }else if(period.getMonths() > 0){
            formatter.appendMonths().appendSuffix("m");
        }else if(period.getWeeks() > 0){
            formatter.appendWeeks().appendSuffix("w");
        }else if(period.getDays() > 0){
            formatter.appendDays().appendSuffix("d");
        }else if(period.getHours() > 0){
            formatter.appendHours().appendSuffix("h");
        }else return "now";

        return formatter.toFormatter().print(period);
    }


    public static String getLocaleFormattedDate(Date date){
        try{
            LocalDate dt = new LocalDate(date);
            LocalDate tomorrow = new LocalDate().plusDays(1);
            LocalDate yesterday = new LocalDate().minusDays(1);
            LocalDate today = new LocalDate();

            if(dt.equals(today)) {
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                return "Today, "+df.format(date);
            }else if(dt.equals(tomorrow)) {
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                return "Tomorrow, "+df.format(date);
            }else if(dt.equals(yesterday)) {
                return "Yesterday";
            }else {
                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM, hh:mm a");
                return df.format(date);
            }

        }catch (Exception e){
            e.printStackTrace();

            if(date != null) {
                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM, hh:mm a");
                return df.format(date);
            }else return "";
        }
    }

    public static String getLocaleFormattedDateOnly(Date date){
        try{
            LocalDate dt = new LocalDate(date);
            LocalDate tomorrow = new LocalDate().plusDays(1);
            LocalDate yesterday = new LocalDate().minusDays(1);
            LocalDate today = new LocalDate();

            if(dt.equals(today)) {
                return "Today";
            }else if(dt.equals(tomorrow)) {
                return "Tomorrow";
            }else if(dt.equals(yesterday)) {
                return "Yesterday";
            }else {
                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                return df.format(date);
            }

        }catch (Exception e){
            e.printStackTrace();

            if(date != null) {
                SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                return df.format(date);
            }else return "";
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
