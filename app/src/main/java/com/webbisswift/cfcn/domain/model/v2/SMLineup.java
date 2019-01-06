package com.webbisswift.cfcn.domain.model.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMLineup {

    List<SMLineupPlayer> data;


    public SMLineup() {
    }




    public List<SMLineupPlayer> getData() {
        return data;
    }

    public void setData(List<SMLineupPlayer> data) {
        this.data = data;
    }

    public static class SMLineupPlayer{

        public SMLineupPlayer() {
        }

        int team_id, player_id, number, formation_position;
        String player_name, position;
        double posx, posy;
        SMPlayer player;
        SMLineupStats stats;
        static HashMap<String, String> positions = new HashMap<>();

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getFormation_position() {
            return formation_position;
        }

        public void setFormation_position(int formation_position) {
            this.formation_position = formation_position;
        }

        public String getPlayer_name() {
            return player_name;
        }

        public void setPlayer_name(String player_name) {
            this.player_name = player_name;
        }

        public String getPosition() {
            String posStr =  positions.get(position);
            if(posStr==null || posStr.isEmpty()) return position;
            else return posStr;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public double getPosx() {
            return posx;
        }

        public void setPosx(double posx) {
            this.posx = posx;
        }

        public double getPosy() {
            return posy;
        }

        public void setPosy(double posy) {
            this.posy = posy;
        }

        public SMPlayer getPlayer() {
            return player;
        }

        public void setPlayer(SMPlayer player) {
            this.player = player;
        }

        public SMLineupStats getStats() {
            return stats;
        }

        public void setStats(SMLineupStats stats) {
            this.stats = stats;
        }


        static {
            positions.put("G", "Goalkeeper");
            positions.put("D","Defender");
            positions.put("M","Midfielder");
            positions.put("F","Forward");

        }
    }

    public static class SMLineupStats{
        SMLineupStatsShots shots;
        SMLineupStatsFouls fouls;
        SMLineupStatsCards cards;
        SMLineupStatsPassing passing;
        SMLineupStatsGoals goals;
        SMLineupStatsOverall other;

        public SMLineupStats() {
        }

        public SMLineupStatsShots getShots() {
            return shots;
        }

        public void setShots(SMLineupStatsShots shots) {
            this.shots = shots;
        }

        public SMLineupStatsFouls getFouls() {
            return fouls;
        }

        public void setFouls(SMLineupStatsFouls fouls) {
            this.fouls = fouls;
        }

        public SMLineupStatsCards getCards() {
            return cards;
        }

        public void setCards(SMLineupStatsCards cards) {
            this.cards = cards;
        }

        public SMLineupStatsPassing getPassing() {
            return passing;
        }

        public void setPassing(SMLineupStatsPassing passing) {
            this.passing = passing;
        }

        public SMLineupStatsGoals getGoals() {
            return goals;
        }

        public void setGoals(SMLineupStatsGoals goals) {
            this.goals = goals;
        }

        public SMLineupStatsOverall getOther() {
            return other;
        }

        public void setOther(SMLineupStatsOverall other) {
            this.other = other;
        }
    }

    public static class SMLineupStatsShots{

        public SMLineupStatsShots() {
        }

        int shots_total, shots_on_goal;

        public int getShots_total() {
            return shots_total;
        }

        public void setShots_total(int shots_total) {
            this.shots_total = shots_total;
        }

        public int getShots_on_goal() {
            return shots_on_goal;
        }

        public void setShots_on_goal(int shots_on_goal) {
            this.shots_on_goal = shots_on_goal;
        }
    }

    public static class SMLineupStatsGoals{

        public SMLineupStatsGoals() {
        }

        int scored, conceded;

        public int getScored() {
            return scored;
        }

        public void setScored(int scored) {
            this.scored = scored;
        }

        public int getConceded() {
            return conceded;
        }

        public void setConceded(int conceded) {
            this.conceded = conceded;
        }
    }

    public static class SMLineupStatsFouls{

        public SMLineupStatsFouls() {
        }

        int drawn, commited;

        public int getDrawn() {
            return drawn;
        }

        public void setDrawn(int drawn) {
            this.drawn = drawn;
        }

        public int getCommited() {
            return commited;
        }

        public void setCommited(int commited) {
            this.commited = commited;
        }
    }

    public static class SMLineupStatsCards{

        public SMLineupStatsCards() {
        }

        int yellowcards, redcards;

        public int getYellowcards() {
            return yellowcards;
        }

        public void setYellowcards(int yellowcards) {
            this.yellowcards = yellowcards;
        }

        public int getRedcards() {
            return redcards;
        }

        public void setRedcards(int redcards) {
            this.redcards = redcards;
        }
    }

    public static class SMLineupStatsPassing{

        public SMLineupStatsPassing() {
        }

        int total_crosses, crosses_accuracy, passes, passet_accuracy;

        public int getTotal_crosses() {
            return total_crosses;
        }

        public void setTotal_crosses(int total_crosses) {
            this.total_crosses = total_crosses;
        }

        public int getCrosses_accuracy() {
            return crosses_accuracy;
        }

        public void setCrosses_accuracy(int crosses_accuracy) {
            this.crosses_accuracy = crosses_accuracy;
        }

        public int getPasses() {
            return passes;
        }

        public void setPasses(int passes) {
            this.passes = passes;
        }

        public int getPasset_accuracy() {
            return passet_accuracy;
        }

        public void setPasset_accuracy(int passet_accuracy) {
            this.passet_accuracy = passet_accuracy;
        }
    }

    public static class SMLineupStatsOverall{

        public SMLineupStatsOverall() {
        }

        int assists, offsides, saves, pen_scored, pen_missed, pen_saved, tackles, blocks, interceptions, clearances, minutes_played;

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getOffsides() {
            return offsides;
        }

        public void setOffsides(int offsides) {
            this.offsides = offsides;
        }

        public int getSaves() {
            return saves;
        }

        public void setSaves(int saves) {
            this.saves = saves;
        }

        public int getPen_scored() {
            return pen_scored;
        }

        public void setPen_scored(int pen_scored) {
            this.pen_scored = pen_scored;
        }

        public int getPen_missed() {
            return pen_missed;
        }

        public void setPen_missed(int pen_missed) {
            this.pen_missed = pen_missed;
        }

        public int getPen_saved() {
            return pen_saved;
        }

        public void setPen_saved(int pen_saved) {
            this.pen_saved = pen_saved;
        }

        public int getTackles() {
            return tackles;
        }

        public void setTackles(int tackles) {
            this.tackles = tackles;
        }

        public int getBlocks() {
            return blocks;
        }

        public void setBlocks(int blocks) {
            this.blocks = blocks;
        }

        public int getInterceptions() {
            return interceptions;
        }

        public void setInterceptions(int interceptions) {
            this.interceptions = interceptions;
        }

        public int getClearances() {
            return clearances;
        }

        public void setClearances(int clearances) {
            this.clearances = clearances;
        }

        public int getMinutes_played() {
            return minutes_played;
        }

        public void setMinutes_played(int minutes_played) {
            this.minutes_played = minutes_played;
        }
    }


}
