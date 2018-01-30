package com.webbisswift.cfcn.domain.model.v2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by biswas on 29/01/2018.
 */



public class SMTime {

    public SMTime() {
    }

    String status;
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
        //if(status.contentEquals("LIVE") || status.contentEquals("HT") || status.contentEquals("PEN_LIVE")){}
        return false;
    }

    public void setStarting_at(MatchTime starting_at) {
        this.starting_at = starting_at;
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
}
