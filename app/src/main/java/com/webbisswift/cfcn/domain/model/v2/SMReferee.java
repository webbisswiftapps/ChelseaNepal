package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/9/18.
 */

public class SMReferee {

    RefereeData data;

    public SMReferee() {
    }

    public RefereeData getData() {
        return data;
    }

    public void setData(RefereeData data) {
        this.data = data;
    }

    public static class RefereeData{
        String common_name,firstname, lastname, fullname;
        int id;

        public RefereeData() {
        }

        public String getCommon_name() {
            return common_name;
        }

        public void setCommon_name(String common_name) {
            this.common_name = common_name;
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

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
