
package com.webbisswift.cfcn.domain.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Facts {

   String comp_id, ft_score, ht_score, id, localteam_id, localteam_score, season, status, time, timer, venue, venue_city, venue_id, visitorteam_id, visitorteam_name, visitorteam_score, week;
   List<FactsMatchEvent> events;

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getFt_score() {
        return ft_score;
    }

    public void setFt_score(String ft_score) {
        this.ft_score = ft_score;
    }

    public String getHt_score() {
        return ht_score;
    }

    public void setHt_score(String ht_score) {
        this.ht_score = ht_score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalteam_id() {
        return localteam_id;
    }

    public void setLocalteam_id(String localteam_id) {
        this.localteam_id = localteam_id;
    }

    public String getLocalteam_score() {
        return localteam_score;
    }

    public void setLocalteam_score(String localteam_score) {
        this.localteam_score = localteam_score;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenue_city() {
        return venue_city;
    }

    public void setVenue_city(String venue_city) {
        this.venue_city = venue_city;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getVisitorteam_id() {
        return visitorteam_id;
    }

    public void setVisitorteam_id(String visitorteam_id) {
        this.visitorteam_id = visitorteam_id;
    }

    public String getVisitorteam_name() {
        return visitorteam_name;
    }

    public void setVisitorteam_name(String visitorteam_name) {
        this.visitorteam_name = visitorteam_name;
    }

    public String getVisitorteam_score() {
        return visitorteam_score;
    }

    public void setVisitorteam_score(String visitorteam_score) {
        this.visitorteam_score = visitorteam_score;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<FactsMatchEvent> getEvents() {
        return events;
    }

    public void setEvents(List<FactsMatchEvent> events) {
        this.events = events;
    }


    @Override
    public String toString() {
        return id+" "+ft_score+" "+ht_score+" "+venue;
    }
}
