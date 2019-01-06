package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/11/18.
 */

public class SMPlayingLeague {
    String name, country;
    int id;
    boolean is_cup;
    boolean is_ad;

    public SMPlayingLeague() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_cup() {
        return is_cup;
    }

    public void setIs_cup(boolean is_cup) {
        this.is_cup = is_cup;
    }

    public int getPriority(){
        if(!is_cup){
            return 0;
        }else return id;
    }

    public boolean isIs_ad() {
        return is_ad;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIs_ad(boolean is_ad) {
        this.is_ad = is_ad;
    }





}
