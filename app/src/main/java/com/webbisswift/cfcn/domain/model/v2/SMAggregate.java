package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/12/18.
 */

public class SMAggregate {

    AggregateData data;

    public SMAggregate() {
    }

    public AggregateData getData() {
        return data;
    }

    public void setData(AggregateData data) {
        this.data = data;
    }

    public static class AggregateData{
        int id, league_id, localteam_id, visitorteam_id, season_id, stage_id;
        String localteam, visitorteam, result;

        public AggregateData() {
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

        public int getLocalteam_id() {
            return localteam_id;
        }

        public void setLocalteam_id(int localteam_id) {
            this.localteam_id = localteam_id;
        }

        public int getVisitorteam_id() {
            return visitorteam_id;
        }

        public void setVisitorteam_id(int visitorteam_id) {
            this.visitorteam_id = visitorteam_id;
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

        public String getLocalteam() {
            return localteam;
        }

        public void setLocalteam(String localteam) {
            this.localteam = localteam;
        }

        public String getVisitorteam() {
            return visitorteam;
        }

        public void setVisitorteam(String visitorteam) {
            this.visitorteam = visitorteam;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
