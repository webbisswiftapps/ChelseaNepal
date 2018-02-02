package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMScore {

    public SMScore() {
    }

    String ht_score;
    int localteam_pen_score, visitorteam_pen_score, localteam_score, visitorteam_score;

    public String getHt_score() {
        return ht_score;
    }

    public void setHt_score(String ht_score) {
        this.ht_score = ht_score;
    }

    public String getLocalteam_pen_score() {
        return String.valueOf(localteam_pen_score);
    }

    public void setLocalteam_pen_score(int localteam_pen_score) {
        this.localteam_pen_score = localteam_pen_score;
    }

    public String getVisitorteam_pen_score() {
        return String.valueOf(visitorteam_pen_score);
    }

    public void setVisitorteam_pen_score(int visitorteam_pen_score) {
        this.visitorteam_pen_score = visitorteam_pen_score;
    }

    public String getLocalteam_score() {
        return String.valueOf(localteam_score);
    }

    public void setLocalteam_score(int localteam_score) {
        this.localteam_score = localteam_score;
    }

    public String getVisitorteam_score() {
        return String.valueOf(visitorteam_score);
    }

    public void setVisitorteam_score(int visitorteam_score) {
        this.visitorteam_score = visitorteam_score;
    }
}
