package com.webbisswift.cfcn.domain.model;

import java.util.List;

/**
 * Created by apple on 12/28/17.
 */

public class MatchLiveStatus {
    public String status;
    public boolean started;
    public List<Long> score;
    public MatchFactsHolder match_facts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<Long> getScore() {
        return score;
    }

    public void setScore(List<Long> score) {
        this.score = score;
    }

    public String getHomeScore(){
        if(score != null & score.size() == 2){
            return score.get(0).toString();
        }else return "0";
    }

    public String getAwayScore(){
        if(score != null & score.size() == 2){
            return score.get(1).toString();
        }else return "0";
    }
}
