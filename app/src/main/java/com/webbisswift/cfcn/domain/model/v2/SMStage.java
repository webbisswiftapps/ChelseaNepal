package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMStage {

    StageData data;

    public SMStage() {
    }

    public StageData getData() {
        return data;
    }

    public void setData(StageData data) {
        this.data = data;
    }

    public static class StageData{
        int id, league_id, season_id;
        String name;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
