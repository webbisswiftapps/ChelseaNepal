package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMTeam {

    public SMTeam() {
    }

    TeamData data;

    public TeamData getData() {
        return data;
    }

    public void setData(TeamData data) {
        this.data = data;
    }

    public static class TeamData{

        public TeamData() {
        }

        int country_id, founded, id, legacy_id, venue_id;
        boolean national_team;
        String name, short_code, logo_path, twitter;

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public int getFounded() {
            return founded;
        }

        public void setFounded(int founded) {
            this.founded = founded;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLegacy_id() {
            return legacy_id;
        }

        public void setLegacy_id(int legacy_id) {
            this.legacy_id = legacy_id;
        }

        public int getVenue_id() {
            return venue_id;
        }

        public void setVenue_id(int venue_id) {
            this.venue_id = venue_id;
        }

        public boolean isNational_team() {
            return national_team;
        }

        public void setNational_team(boolean national_team) {
            this.national_team = national_team;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getShort_code() {
            return short_code;
        }

        public void setShort_code(String short_code) {
            this.short_code = short_code;
        }


        public String getMinimalName(){
            if(short_code!=null && !short_code.isEmpty()) return short_code;
            else return name;
        }
    }

}
