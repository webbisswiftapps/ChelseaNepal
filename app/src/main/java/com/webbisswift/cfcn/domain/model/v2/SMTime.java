package com.webbisswift.cfcn.domain.model.v2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by biswas on 29/01/2018.
 */



public class SMTime {




    public SMTime() {
    }

    String status;
    int minute;
    int injury_time;
    MatchTime starting_at;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MatchTime getStarting_at() {
        return starting_at;
    }

    public boolean isLive(){
        return status_live.get(status);
    }

    public String getStatusDescription(){
        String desc = statuses.get(status);
        if(desc== null)
            desc = "";
        return desc;
    }

    public boolean showPenalties(){
        return (status.contentEquals("FT_PEN") || status.contentEquals("PEN_LIVE"));
    }

    public void setStarting_at(MatchTime starting_at) {
        this.starting_at = starting_at;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getInjury_time() {
        return injury_time;
    }

    public void setInjury_time(int injury_time) {
        this.injury_time = injury_time;
    }

    public static class MatchTime{

        public MatchTime() {
        }

        String date, date_time, time, timezone;
        long timestamp;
        Date startDateTime;



        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public Date getStartDateTime(){

            if(startDateTime == null) {
                //"Wednesday,  6 December 2017 1:30"
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone(timezone));

                try {
                    this.startDateTime= formatter.parse(date_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                    this.startDateTime = null;
                }
            }

            return startDateTime;
        }
    }


    private static final HashMap<String, String> statuses = new HashMap<>();
    private static final HashMap<String, Boolean> status_live = new HashMap<>();

    static {
        statuses.put("NS", "Not Started");
        statuses.put("LIVE","Live");
        statuses.put("HT", "Half Time");
        statuses.put("FT", "Full Time");
        statuses.put("ET", "Extra Time");
        statuses.put("PEN_LIVE", "Penalty Shootout");
        statuses.put("AET", "Finished after extra time");
        statuses.put("BREAK","Waiting for Extra Time");
        statuses.put("FT_PEN","Full Time after Penalties");
        statuses.put("CANCL", "Match Cancelled");
        statuses.put("POSTP","Match Postponed");
        statuses.put("INT","Match Interrupted.");
        statuses.put("ABAN", "Match Abandoned");
        statuses.put("SUSP", "Match Suspended");
        statuses.put("AWARDED","Match Awarded");
        statuses.put("DELAYED","Match Delayed");
        statuses.put("TBA", "To be Announced");
        statuses.put("WO","Walkover");
        statuses.put("AU", "Awaiting Updates");
    }

    static {
        status_live.put("NS", false);
        status_live.put("LIVE",true);
        status_live.put("HT", true);
        status_live.put("FT", false);
        status_live.put("ET", true);
        status_live.put("PEN_LIVE", true);
        status_live.put("AET", false);
        status_live.put("BREAK",true);
        status_live.put("FT_PEN",false);
        status_live.put("CANCL", false);
        status_live.put("POSTP",false);
        status_live.put("INT", false);
        status_live.put("ABAN", false);
        status_live.put("SUSP", false);
        status_live.put("AWARDED",false);
        status_live.put("DELAYED",false);
        status_live.put("TBA", false);
        status_live.put("WO",false);
        status_live.put("AU", false);
    }
}
