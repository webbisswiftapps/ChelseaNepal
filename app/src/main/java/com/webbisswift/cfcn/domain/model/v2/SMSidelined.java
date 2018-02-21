package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by apple on 2/10/18.
 */

public class SMSidelined {

    List<SidelinedPlayer> data;

    public SMSidelined() {
    }

    public List<SidelinedPlayer> getData() {
        return data;
    }

    public void setData(List<SidelinedPlayer> data) {
        this.data = data;
    }

    public static class SidelinedPlayer{
        String description, end_date, start_date;
        int team_id, player_id;
        SMPlayer player;

        public SidelinedPlayer() {
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getPlayer_id() {
            return player_id;
        }

        public void setPlayer_id(int player_id) {
            this.player_id = player_id;
        }

        public SMPlayer getPlayer() {
            return player;
        }

        public void setPlayer(SMPlayer player) {
            this.player = player;
        }
    }
}
