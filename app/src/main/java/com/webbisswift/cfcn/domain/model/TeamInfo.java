package com.webbisswift.cfcn.domain.model;

import java.util.List;

/**
 * Created by biswas on 21/12/2017.
 */

public class TeamInfo {

    String coach_id, coach_name, country, founded, name, venue_address, venue_capacity, venue_city, venue_name, venue_surface;
    List<SquadPlayer> squad;
    List<InjuredPlayer> sidelined;
    List<TrasnferInPlayer> transfers_in;
    List<TransferOutPlayer> transfers_out;


    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue_address() {
        return venue_address;
    }

    public void setVenue_address(String venue_address) {
        this.venue_address = venue_address;
    }

    public String getVenue_capacity() {
        return venue_capacity;
    }

    public void setVenue_capacity(String venue_capacity) {
        this.venue_capacity = venue_capacity;
    }

    public String getVenue_city() {
        return venue_city;
    }

    public void setVenue_city(String venue_city) {
        this.venue_city = venue_city;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenue_surface() {
        return venue_surface;
    }

    public void setVenue_surface(String venue_surface) {
        this.venue_surface = venue_surface;
    }

    public List<SquadPlayer> getSquad() {
        return squad;
    }

    public void setSquad(List<SquadPlayer> squad) {
        this.squad = squad;
    }

    public List<InjuredPlayer> getSidelined() {
        return sidelined;
    }

    public void setSidelined(List<InjuredPlayer> sidelined) {
        this.sidelined = sidelined;
    }

    public List<TrasnferInPlayer> getTransfers_in() {
        return transfers_in;
    }

    public void setTransfers_in(List<TrasnferInPlayer> transfers_in) {
        this.transfers_in = transfers_in;
    }

    public List<TransferOutPlayer> getTransfers_out() {
        return transfers_out;
    }

    public void setTransfers_out(List<TransferOutPlayer> transfers_out) {
        this.transfers_out = transfers_out;
    }
}
