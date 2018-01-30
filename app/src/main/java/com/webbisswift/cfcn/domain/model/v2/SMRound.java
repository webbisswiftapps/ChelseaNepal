package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMRound {
    RoundData data;

    public SMRound() {
    }

    public RoundData getData() {
        return data;
    }

    public void setData(RoundData data) {
        this.data = data;
    }

    public static class RoundData{

        public RoundData() {
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLeague_id() {
            return league_id;
        }

        public void setLeague_id(int league_id) {
            this.league_id = league_id;
        }

        public int getSeason_id() {
            return season_id;
        }

        public void setSeason_id(int season_id) {
            this.season_id = season_id;
        }

        public int getStage_id() {
            return stage_id;
        }

        public void setStage_id(int stage_id) {
            this.stage_id = stage_id;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }

        String end, start;
        int id, league_id, season_id, stage_id, name;
    }
}
