package com.webbisswift.cfcnepal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by apple on 11/30/17.
 */

public class Utilities {


    public static String getCopyrightDate(){
        try {
            DateTime now = new DateTime();
            return "© " + now.year();
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

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
