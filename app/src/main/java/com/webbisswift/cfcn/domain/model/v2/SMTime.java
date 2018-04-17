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
    int extra_minute;
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

        if(isLive()){
            desc += " "+minute;
            if(extra_minute > 0){
                desc+= "+"+extra_minute+"'";
            }else desc += "'";
        }
        
        return desc;
    }

    public boolean showPenalties(){
        return (status.contentEquals("FT_PEN") || status.contentEquals("PEN_LIVE"));
    }

    public boolean isFinished(){
        return status_finished.get(status);
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

    public int getExtra_minute() {
        return extra_minute;
    }

    public void setExtra_minute(int extra_minute) {
        this.extra_minute = extra_minute;
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
    private static final HashMap<String, Boolean> status_finished = new HashMap<>();

    static {
        statuses.put("NS", "Not Started");
        statuses.put("LIVE","Live");
        statuses.put("HT", "Half Time");
        statuses.put("FT", "Full Time");
        statuses.put("ET", "Extra Time");
        statuses.put("PEN_LIVE", "Penalty Shootout");
        statuses.put("AET", "After Extra Time");
        statuses.put("BREAK","Extra Time");
        statuses.put("FT_PEN","Full Time (PEN)");
        statuses.put("CANCL", "Cancelled");
        statuses.put("POSTP","Postponed");
        statuses.put("INT","Interrupted");
        statuses.put("ABAN", "Abandoned");
        statuses.put("SUSP", "Suspended");
        statuses.put("AWARDED","Awarded");
        statuses.put("DELAYED","Delayed");
        statuses.put("TBA", "TBA");
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

    static {
        status_finished.put("NS", false);
        status_finished.put("LIVE",false);
        status_finished.put("HT", false);
        status_finished.put("FT", true);
        status_finished.put("ET", false);
        status_finished.put("PEN_LIVE", false);
        status_finished.put("AET", true);
        status_finished.put("BREAK",false);
        status_finished.put("FT_PEN",true);
        status_finished.put("CANCL", false);
        status_finished.put("POSTP",false);
        status_finished.put("INT", false);
        status_finished.put("ABAN", false);
        status_finished.put("SUSP", false);
        status_finished.put("AWARDED",false);
        status_finished.put("DELAYED",false);
        status_finished.put("TBA", false);
        status_finished.put("WO",false);
        status_finished.put("AU", false);
    }
}
