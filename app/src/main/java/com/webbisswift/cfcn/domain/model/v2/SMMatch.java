package com.webbisswift.cfcn.domain.model.v2;

import java.util.HashMap;
import java.util.List;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMMatch {

    public SMMatch(){}

    boolean commentaries, winning_odds_calculated;
    int id, localteam_id, visitorteam_id, league_id, stage_id, round_id, venue_id;
    SMLeague league;
    SMCoach localCoach, visitorCoach;
    SMTeam  localTeam, visitorTeam;
    SMRound round;
    SMStage stage;
    SMScore scores;
    SMTime time;
    SMVenue venue;
    SMEvents events;
    SMFormations formations;
    SMWeather weather;
    SMStats stats;
    List<SMMatch> headtohead;
    HashMap<String, String> tv_guide_all;
    SMStandings standings;
    SMLineup lineup;
    SMLineup bench;

    public boolean isCommentaries() {
        return commentaries;
    }

    public void setCommentaries(boolean commentaries) {
        this.commentaries = commentaries;
    }

    public boolean isWinning_odds_calculated() {
        return winning_odds_calculated;
    }

    public void setWinning_odds_calculated(boolean winning_odds_calculated) {
        this.winning_odds_calculated = winning_odds_calculated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocalteam_id() {
        return localteam_id;
    }

    public void setLocalteam_id(int localteam_id) {
        this.localteam_id = localteam_id;
    }

    public int getVisitorteam_id() {
        return visitorteam_id;
    }

    public void setVisitorteam_id(int visitorteam_id) {
        this.visitorteam_id = visitorteam_id;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getStage_id() {
        return stage_id;
    }

    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
    }

    public int getRound_id() {
        return round_id;
    }

    public void setRound_id(int round_id) {
        this.round_id = round_id;
    }

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }

    public SMLeague getLeague() {
        return league;
    }

    public void setLeague(SMLeague league) {
        this.league = league;
    }

    public SMCoach getLocalCoach() {
        return localCoach;
    }

    public void setLocalCoach(SMCoach localCoach) {
        this.localCoach = localCoach;
    }

    public SMCoach getVisitorCoach() {
        return visitorCoach;
    }

    public void setVisitorCoach(SMCoach visitorCoach) {
        this.visitorCoach = visitorCoach;
    }

    public SMTeam getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(SMTeam localTeam) {
        this.localTeam = localTeam;
    }

    public SMTeam getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(SMTeam visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public SMRound getRound() {
        return round;
    }

    public void setRound(SMRound round) {
        this.round = round;
    }

    public SMStage getStage() {
        return stage;
    }

    public void setStage(SMStage stage) {
        this.stage = stage;
    }

    public SMScore getScores() {
        return scores;
    }

    public void setScores(SMScore score) {
        this.scores = score;
    }

    public SMTime getTime() {
        return time;
    }

    public void setTime(SMTime time) {
        this.time = time;
    }

    public SMVenue getVenue() {
        return venue;
    }

    public void setVenue(SMVenue venue) {
        this.venue = venue;
    }

    public SMEvents getEvents() {
        return events;
    }

    public void setEvents(SMEvents events) {
        this.events = events;
    }

    public SMFormations getFormations() {
        return formations;
    }

    public void setFormations(SMFormations formations) {
        this.formations = formations;
    }

    public SMWeather getWeather() {
        return weather;
    }

    public void setWeather(SMWeather weather) {
        this.weather = weather;
    }

    public SMStats getStats() {
        return stats;
    }

    public void setStats(SMStats stats) {
        this.stats = stats;
    }

    public List<SMMatch> getHeadtohead() {
        return headtohead;
    }

    public void setHeadtohead(List<SMMatch> headtohead) {
        this.headtohead = headtohead;
    }

    public HashMap<String, String> getTv_guide_all() {
        return tv_guide_all;
    }

    public void setTv_guide_all(HashMap<String, String> tv_guide_all) {
        this.tv_guide_all = tv_guide_all;
    }

    public SMStandings getStandings() {
        return standings;
    }

    public void setStandings(SMStandings standings) {
        this.standings = standings;
    }

    public SMLineup getLineup() {
        return lineup;
    }

    public void setLineup(SMLineup lineup) {
        this.lineup = lineup;
    }

    public SMLineup getBench() {
        return bench;
    }

    public void setBench(SMLineup bench) {
        this.bench = bench;
    }



    /* Custom Methods */
    public String getCompetitionDesc(){
        if(league.data.is_cup){
            return league.data.name+" "+stage.data.name;
        }else{
            return league.data.name+", Matchday "+round.data.name;
        }
    }
}