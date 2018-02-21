package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/16/18.
 */

public class SMUefaRanking {

    public SMUefaRankingData data;

    public SMUefaRanking() {
    }


    public static class SMUefaRankingData{
        public int coeffiecient, points, position, position_won_or_lost, team_id;
        public String position_status;

        public SMUefaRankingData() {
        }

    }
}
