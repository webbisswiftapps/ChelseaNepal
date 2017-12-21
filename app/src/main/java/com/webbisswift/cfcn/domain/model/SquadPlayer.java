package com.webbisswift.cfcn.domain.model;

/**
 * Created by biswas on 21/12/2017.
 */

public class SquadPlayer {

    String age, appearences, assists, goals,
            id, lineups, minutes, name, number, position,
            redcards, substitute_in, substitute_out,
            substitutes_on_bench, yellowcards, yellowred;

    boolean injured;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAppearances() {
        return appearences;
    }

    public void setAppearances(String appearances) {
        this.appearences = appearances;
    }

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineups() {
        return lineups;
    }

    public void setLineups(String lineups) {
        this.lineups = lineups;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRedcards() {
        return redcards;
    }

    public void setRedcards(String redcards) {
        this.redcards = redcards;
    }

    public String getSubstitute_in() {
        return substitute_in;
    }

    public void setSubstitute_in(String substitute_in) {
        this.substitute_in = substitute_in;
    }

    public String getSubstitute_out() {
        return substitute_out;
    }

    public void setSubstitute_out(String substitute_out) {
        this.substitute_out = substitute_out;
    }

    public String getSubstitutes_on_bench() {
        return substitutes_on_bench;
    }

    public void setSubstitutes_on_bench(String substitutes_on_bench) {
        this.substitutes_on_bench = substitutes_on_bench;
    }

    public String getYellowcards() {
        return yellowcards;
    }

    public void setYellowcards(String yellowcards) {
        this.yellowcards = yellowcards;
    }

    public String getYellowred() {
        return yellowred;
    }

    public void setYellowred(String yellowred) {
        this.yellowred = yellowred;
    }


    public String getPositionName(){

        if(position.contentEquals("G"))
            return "Goalkeeper";
        else if(position.contentEquals("D"))
            return "Defence";
        else if(position.contentEquals("M"))
            return "Midfield";
        else if(position.contentEquals("A"))
            return "Attack";
        else return "Unknown";

    }



}
