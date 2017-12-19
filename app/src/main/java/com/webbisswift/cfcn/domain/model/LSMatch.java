
package com.webbisswift.cfcn.domain.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LSMatch {

    @SerializedName("away")
    private String mAway;
    @SerializedName("awayfull")
    private String mAwayfull;
    @SerializedName("awaypath")
    private String mAwaypath;
    @SerializedName("awayshirt")
    private String mAwayshirt;
    @SerializedName("competition")
    private String mCompetition;
    @SerializedName("competitionkey")
    private String mCompetitionkey;
    @SerializedName("events")
    private List<MatchEvent> mEvents;
    @SerializedName("home")
    private String mHome;
    @SerializedName("homefull")
    private String mHomefull;
    @SerializedName("homepath")
    private String mHomepath;
    @SerializedName("homeshirt")
    private String mHomeshirt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("score")
    private List<Long> mScore;
    @SerializedName("started")
    private Boolean mStarted;
    @SerializedName("status")
    private String mStatus;

    public String getAway() {
        return mAway;
    }

    public void setAway(String away) {
        mAway = away;
    }

    public String getAwayfull() {
        return mAwayfull;
    }

    public void setAwayfull(String awayfull) {
        mAwayfull = awayfull;
    }

    public String getAwaypath() {
        return mAwaypath;
    }

    public void setAwaypath(String awaypath) {
        mAwaypath = awaypath;
    }

    public String getAwayshirt() {
        return mAwayshirt;
    }

    public void setAwayshirt(String awayshirt) {
        mAwayshirt = awayshirt;
    }

    public String getCompetition() {
        return mCompetition;
    }

    public void setCompetition(String competition) {
        mCompetition = competition;
    }

    public String getCompetitionkey() {
        return mCompetitionkey;
    }

    public void setCompetitionkey(String competitionkey) {
        mCompetitionkey = competitionkey;
    }

    public List<MatchEvent> getEvents() {
        return mEvents;
    }

    public void setEvents(List<MatchEvent> events) {
        mEvents = events;
    }

    public String getHome() {
        return mHome;
    }

    public void setHome(String home) {
        mHome = home;
    }

    public String getHomefull() {
        return mHomefull;
    }

    public void setHomefull(String homefull) {
        mHomefull = homefull;
    }

    public String getHomepath() {
        return mHomepath;
    }

    public void setHomepath(String homepath) {
        mHomepath = homepath;
    }

    public String getHomeshirt() {
        return mHomeshirt;
    }

    public void setHomeshirt(String homeshirt) {
        mHomeshirt = homeshirt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<Long> getScore() {
        return mScore;
    }

    public void setScore(List<Long> score) {
        mScore = score;
    }

    public Boolean getStarted() {
        return mStarted;
    }

    public void setStarted(Boolean started) {
        mStarted = started;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
