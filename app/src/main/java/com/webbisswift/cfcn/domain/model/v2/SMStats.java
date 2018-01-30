package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMStats {

    List<StatsData> data;

    public SMStats() {
    }

    public List<StatsData> getData() {
        return data;
    }

    public void setData(List<StatsData> data) {
        this.data = data;
    }

    public static class StatsData{

        public StatsData() {
        }

        int team_id, throw_in, substitutions, saves, redcards, possesiontime, offsides, goal_kick, free_kick, fouls, corners;
        long fixture_id;
        Attacks attacks;
        Passes passes;
        Shots shots;

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getThrow_in() {
            return throw_in;
        }

        public void setThrow_in(int throw_in) {
            this.throw_in = throw_in;
        }

        public int getSubstitutions() {
            return substitutions;
        }

        public void setSubstitutions(int substitutions) {
            this.substitutions = substitutions;
        }

        public int getSaves() {
            return saves;
        }

        public void setSaves(int saves) {
            this.saves = saves;
        }

        public int getRedcards() {
            return redcards;
        }

        public void setRedcards(int redcards) {
            this.redcards = redcards;
        }

        public int getPossesiontime() {
            return possesiontime;
        }

        public void setPossesiontime(int possesiontime) {
            this.possesiontime = possesiontime;
        }

        public int getOffsides() {
            return offsides;
        }

        public void setOffsides(int offsides) {
            this.offsides = offsides;
        }

        public int getGoal_kick() {
            return goal_kick;
        }

        public void setGoal_kick(int goal_kick) {
            this.goal_kick = goal_kick;
        }

        public int getFree_kick() {
            return free_kick;
        }

        public void setFree_kick(int free_kick) {
            this.free_kick = free_kick;
        }

        public int getFouls() {
            return fouls;
        }

        public void setFouls(int fouls) {
            this.fouls = fouls;
        }

        public int getCorners() {
            return corners;
        }

        public void setCorners(int corners) {
            this.corners = corners;
        }

        public long getFixture_id() {
            return fixture_id;
        }

        public void setFixture_id(long fixture_id) {
            this.fixture_id = fixture_id;
        }

        public Attacks getAttacks() {
            return attacks;
        }

        public void setAttacks(Attacks attacks) {
            this.attacks = attacks;
        }

        public Passes getPasses() {
            return passes;
        }

        public void setPasses(Passes passes) {
            this.passes = passes;
        }

        public Shots getShots() {
            return shots;
        }

        public void setShots(Shots shots) {
            this.shots = shots;
        }
    }

    class Attacks{
        int attacks, dangerous_attacks;
    }

    class Passes{
        int accurate, percentage, total;
    }

    class Shots{
        int blocked, insidebox, offgoal, ongoal, outsidebox, total;
    }
}
