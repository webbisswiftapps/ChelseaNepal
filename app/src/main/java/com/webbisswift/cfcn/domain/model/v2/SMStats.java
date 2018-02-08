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

        int team_id, throw_in, substitutions, saves, yellowcards, redcards, possessiontime, offsides, goal_kick, free_kick, fouls, corners;
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

        public int getYellowcards() {
            return yellowcards;
        }

        public void setYellowcards(int yellowcards) {
            this.yellowcards = yellowcards;
        }

        public int getPossessiontime() {
            return possessiontime;
        }

        public void setPossessiontime(int possessiontime) {
            this.possessiontime = possessiontime;
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

    public static class Attacks{
        public Attacks() {
        }

        int attacks, dangerous_attacks;

        public int getAttacks() {
            return attacks;
        }

        public void setAttacks(int attacks) {
            this.attacks = attacks;
        }

        public int getDangerous_attacks() {
            return dangerous_attacks;
        }

        public void setDangerous_attacks(int dangerous_attacks) {
            this.dangerous_attacks = dangerous_attacks;
        }
    }

    public static class Passes{
        int accurate, percentage, total;

        public Passes() {
        }

        public int getAccurate() {
            return accurate;
        }

        public void setAccurate(int accurate) {
            this.accurate = accurate;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class Shots{
        int blocked, insidebox, offgoal, ongoal, outsidebox, total;

        public Shots() {
        }

        public int getBlocked() {
            return blocked;
        }

        public void setBlocked(int blocked) {
            this.blocked = blocked;
        }

        public int getInsidebox() {
            return insidebox;
        }

        public void setInsidebox(int insidebox) {
            this.insidebox = insidebox;
        }

        public int getOffgoal() {
            return offgoal;
        }

        public void setOffgoal(int offgoal) {
            this.offgoal = offgoal;
        }

        public int getOngoal() {
            return ongoal;
        }

        public void setOngoal(int ongoal) {
            this.ongoal = ongoal;
        }

        public int getOutsidebox() {
            return outsidebox;
        }

        public void setOutsidebox(int outsidebox) {
            this.outsidebox = outsidebox;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
