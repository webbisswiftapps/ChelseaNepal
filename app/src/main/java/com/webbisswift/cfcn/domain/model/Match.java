
package com.webbisswift.cfcn.domain.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by apple on 12/3/17.
 */

public class Match {


    public static final int TYPE_FIXTURE = 0;
    public static final int TYPE_RESULT = 1;
    public static final int TYPE_AD = 2;

     public static final String SHIRT_BASE_URL = "https://bluesnp-1727e.firebaseapp.com/";
     public String away, awayfull,  awaypath, awayshirt, competition, home, homepath, homefull, homeshirt, start_date, start_time,tv_guide, status, match_id, competitionkey, country;

     public ArrayList<MatchEvent> events;
     public ArrayList<Integer> score;
     public ArrayList<Integer> penalties;
     public MatchFactsHolder match_facts;
     public MatchLiveStatus live;

     public boolean isAd = false;


    public MatchFactsHolder getMatch_facts() {
        return match_facts;
    }

    public void setMatch_facts(MatchFactsHolder match_facts) {
        this.match_facts = match_facts;
    }

    Date startDateTime;

    public void setAwayfull(String awayfull) {
        this.awayfull = awayfull;
    }

     public String getAwayShirtURL(){

          String jName = awayshirt.substring(0, awayshirt.lastIndexOf('.'));
          return SHIRT_BASE_URL+jName+".png";
     }

     public String getHomeShirtURL(){
          String jName = homeshirt.substring(0, homeshirt.lastIndexOf('.'));
          return SHIRT_BASE_URL+jName+".png";
     }

     public Date getStartDateTime(){

          if(startDateTime == null) {
               //"Wednesday,  6 December 2017 1:30"
               String dateString = start_date+" "+start_time;
               SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy HH:mm");
               formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kathmandu"));

               try {
                    this.startDateTime= formatter.parse(dateString);
               } catch (ParseException e) {
                    e.printStackTrace();
                    this.startDateTime = null;
               }
          }

          return startDateTime;
     }

    public String explain(){
        return home+" vs "+away+" - "+competition+" @ "+start_date+" "+start_time+"  | "+tv_guide;
    }

    public String getHomeScore(){
        if(this.score != null && this.score.size() == 2){
            return this.score.get(0).toString();
        }else return "0";
    }

    public String getAwayScore(){
        if(this.score != null && this.score.size() == 2){
            return this.score.get(1).toString();
        }else return "0";
    }

    public boolean hadPenalties(){
        return (this.penalties != null && this.penalties.size() == 2);
    }

    public String getHomePenaltyScore(){
        if(hadPenalties()){
            return this.penalties.get(0).toString();
        }else return "0";
    }

    public String getAwayPenaltyScore(){
        if(hadPenalties()){
            return this.penalties.get(1).toString();
        }else return "0";
    }

    public String getHomeEvents(){
        if(this.events != null && this.events.size() > 0){

            StringBuilder homeEvents = new StringBuilder();
            for (MatchEvent event: events) {
                if(!event.home.isEmpty()) homeEvents.append(event.minute+" | "+event.type.toUpperCase()+" "+event.home+"\n");
            }
            return  homeEvents.toString();
        }else return "";
    }

    public String getAwayEvents(){
        if(this.events != null && this.events.size() > 0){

            StringBuilder homeEvents = new StringBuilder();
            for (MatchEvent event: events) {
                if(!event.away.isEmpty()) homeEvents.append(event.minute+" | "+event.type.toUpperCase()+" "+event.away+"\n");
            }
            return  homeEvents.toString();
        }else return "";
    }


    public int getMatchType(){
        if(isAd) return TYPE_AD;
        if(status.toUpperCase().contentEquals("FT")) return TYPE_RESULT;
        else return TYPE_FIXTURE;
    }

    public MatchLiveStatus getLive() {
        return live;
    }

    public void setLive(MatchLiveStatus live) {
        this.live = live;
    }

    public Match(){}
}
