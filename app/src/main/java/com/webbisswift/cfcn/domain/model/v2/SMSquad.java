package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by apple on 2/10/18.
 */

public class SMSquad {

    List<SMSquadItem> data;

    public List<SMSquadItem> getData() {
        return data;
    }

    public void setData(List<SMSquadItem> data) {
        this.data = data;
    }

    public SMSquad() {
    }



    public static class SMSquadItem {

        boolean injured;
        int appearences, assists, goals, lineups,
                minutes,
                number, player_id,
                position_id, redcards, substitute_in, substitute_out,
                substitutes_on_bench, yellowcards, yellowred;

        SMPlayer player;

        public SMSquadItem() {
        }

        public int getAppearences() {
            return appearences;
        }

        public void setAppearences(int appearances) {
            this.appearences = appearances;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public int getGoals() {
            return goals;
        }

        public void setGoals(int goals) {
            this.goals = goals;
        }

        public boolean getInjured() {
            return injured;
        }

        public void setInjured(boolean injured) {
            this.injured = injured;
        }

        public int getLineups() {
            return lineups;
        }

        public void setLineups(int lineups) {
            this.lineups = lineups;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public int getPosition_id() {
            return position_id;
        }

        public void setPosition_id(int position_id) {
            this.position_id = position_id;
        }

        public int getRedcards() {
            return redcards;
        }

        public void setRedcards(int redcards) {
            this.redcards = redcards;
        }

        public int getSubstitute_in() {
            return substitute_in;
        }

        public void setSubstitute_in(int substitute_in) {
            this.substitute_in = substitute_in;
        }

        public int getSubstitute_out() {
            return substitute_out;
        }

        public void setSubstitute_out(int substitute_out) {
            this.substitute_out = substitute_out;
        }

        public int getSubstitutes_on_bench() {
            return substitutes_on_bench;
        }

        public void setSubstitutes_on_bench(int substitutes_on_bench) {
            this.substitutes_on_bench = substitutes_on_bench;
        }

        public int getYellowcards() {
            return yellowcards;
        }

        public void setYellowcards(int yellowcards) {
            this.yellowcards = yellowcards;
        }

        public int getYellowred() {
            return yellowred;
        }

        public void setYellowred(int yellowred) {
            this.yellowred = yellowred;
        }

        public SMPlayer getPlayer() {
            return player;
        }

        public void setPlayer(SMPlayer player) {
            this.player = player;
        }


        public String getPositionName(){

            if(position_id == 1)
                return "Goalkeeper";
            else if(position_id == 2)
                return "Defence";
            else if(position_id == 3)
                return "Midfield";
            else if(position_id == 4)
                return "Attack";
            else return "Unknown";

        }
    }
}
