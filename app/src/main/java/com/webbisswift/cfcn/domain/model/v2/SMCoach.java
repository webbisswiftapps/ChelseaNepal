package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMCoach {

    public SMCoach() {
    }

    CoachData data;

    public CoachData getData() {
        return data;
    }

    public void setData(CoachData data) {
        this.data = data;
    }

    public static class CoachData{
        int coach_id;
        String fullname;
        String firstname;
        String lastname;
        String nationality;
        String image_path;


        public CoachData() {
        }

        public int getCoach_id() {
            return coach_id;
        }

        public void setCoach_id(int coach_id) {
            this.coach_id = coach_id;
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

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }
    }
}
