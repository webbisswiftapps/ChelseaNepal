package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMPlayer {
    PlayerData data;

    public SMPlayer() {
    }

    public PlayerData getData() {
        return data;
    }

    public void setData(PlayerData data) {
        this.data = data;
    }

    public static class PlayerData{

        public PlayerData() {
        }

        int player_id, team_id, country_id, position_id;
        String common_name, fullname, firstname,
                lastname, nationality, birthdate,
                birthcountry,birthplace, height, weight,
                image_path;

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public int getPosition_id() {
            return position_id;
        }

        public void setPosition_id(int position_id) {
            this.position_id = position_id;
        }

        public String getCommon_name() {
            return common_name;
        }

        public void setCommon_name(String common_name) {
            this.common_name = common_name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getBirthcountry() {
            return birthcountry;
        }

        public void setBirthcountry(String birthcountry) {
            this.birthcountry = birthcountry;
        }

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }
    }
}
