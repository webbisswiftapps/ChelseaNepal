package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMLeague {

    public enum Leagues{
        PREMIER_LEAGUE (8, 1),
        CHAMPIONS_LEAGUE(2, 2),
        EUROPA_LEAGUE(5,3),
        COMMUNITY_SHIELD(23, 7),
        FA_CUP(24, 4),
        LEAGUE_CUP(27, 5),
        CLUB_FRIENDLIES(1101, 9),
        ICC_CUP(1121, 8),
        UEFA_SUPER_CUP(1328, 6);

        private final int code;
        private final int order;

         Leagues(int code, int order){
            this.code = code;
            this.order = order;
        }


        public int getLeagueCode(){
             return this.code;
        }

        public int getOrder(){
             return this.order;
        }
    }



    public static int getOrderById(int id){

        if(id == Leagues.PREMIER_LEAGUE.code) return  Leagues.PREMIER_LEAGUE.order;
        else if(id == Leagues.CHAMPIONS_LEAGUE.code) return  Leagues.CHAMPIONS_LEAGUE.order;
        else if(id == Leagues.EUROPA_LEAGUE.code) return Leagues.EUROPA_LEAGUE.order;
        else if(id == Leagues.COMMUNITY_SHIELD.code) return  Leagues.COMMUNITY_SHIELD.order;
        else if(id == Leagues.FA_CUP.code) return  Leagues.FA_CUP.order;
        else if(id == Leagues.LEAGUE_CUP.code) return  Leagues.LEAGUE_CUP.order;
        else if(id == Leagues.CLUB_FRIENDLIES.code) return  Leagues.CLUB_FRIENDLIES.order;
        else if(id == Leagues.ICC_CUP.code) return  Leagues.ICC_CUP.order;
        else if(id == Leagues.UEFA_SUPER_CUP.code) return  Leagues.UEFA_SUPER_CUP.order;
        else return 9999;
    }




    public SMLeague() {
    }

    LeagueData data;

    public LeagueData getData() {
        return data;
    }

    public void setData(LeagueData data) {
        this.data = data;
    }

    public static  class LeagueData{

        public LeagueData() {
        }


        int id,legacy_id, country_id, current_round_id, current_season_id, current_stage_id;
        boolean is_cup, live_standings;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLegacy_id() {
            return legacy_id;
        }

        public void setLegacy_id(int legacy_id) {
            this.legacy_id = legacy_id;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public int getCurrent_round_id() {
            return current_round_id;
        }

        public void setCurrent_round_id(int current_round_id) {
            this.current_round_id = current_round_id;
        }

        public int getCurrent_season_id() {
            return current_season_id;
        }

        public void setCurrent_season_id(int current_season_id) {
            this.current_season_id = current_season_id;
        }

        public int getCurrent_stage_id() {
            return current_stage_id;
        }

        public void setCurrent_stage_id(int current_stage_id) {
            this.current_stage_id = current_stage_id;
        }

        public boolean isIs_cup() {
            return is_cup;
        }

        public void setIs_cup(boolean is_cup) {
            this.is_cup = is_cup;
        }

        public boolean isLive_standings() {
            return live_standings;
        }

        public void setLive_standings(boolean live_standings) {
            this.live_standings = live_standings;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
