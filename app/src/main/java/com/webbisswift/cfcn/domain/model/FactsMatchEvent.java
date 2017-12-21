
package com.webbisswift.cfcn.domain.model;

import com.google.gson.annotations.SerializedName;

public class FactsMatchEvent {

   String assist, assist_id, extra_min, id, minute, player, player_id,
    result, team, type;

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getAssist_id() {
        return assist_id;
    }

    public void setAssist_id(String assist_id) {
        this.assist_id = assist_id;
    }

    public String getExtra_min() {
        return extra_min;
    }

    public void setExtra_min(String extra_min) {
        this.extra_min = extra_min;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
