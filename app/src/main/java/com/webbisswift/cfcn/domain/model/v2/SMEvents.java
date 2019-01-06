package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMEvents {
    List<EventData> data;

    public SMEvents() {
    }

    public List<EventData> getData() {
        return data;
    }

    public void setData(List<EventData> data) {
        this.data = data;
    }

    public static class EventData{

        public EventData() {
        }

        String player_name, related_player_name, team_id, type, reason, result;
        int minute, extra_minute, player_id, related_player_id;
        long fixture_id, id;

        public String getPlayer_name() {
            return player_name;
        }

        public void setPlayer_name(String player_name) {
            this.player_name = player_name;
        }

        public String getRelated_player_name() {
            return related_player_name;
        }

        public void setRelated_player_name(String related_player_name) {
            this.related_player_name = related_player_name;
        }


        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getMinute() {
            return minute;
        }

        public int getExtra_minute() {
            return extra_minute;
        }

        public void setExtra_minute(int extra_minute) {
            this.extra_minute = extra_minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public int getRelated_player_id() {
            return related_player_id;
        }

        public void setRelated_player_id(int related_player_id) {
            this.related_player_id = related_player_id;
        }

        public long getFixture_id() {
            return fixture_id;
        }

        public void setFixture_id(long fixture_id) {
            this.fixture_id = fixture_id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }



        public boolean isGoal(){
            return type.contentEquals("goal") || type.contentEquals("penalty")||type.contentEquals("own_goal");
        }
    }
}
