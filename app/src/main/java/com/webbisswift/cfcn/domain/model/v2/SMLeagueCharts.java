package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by apple on 2/12/18.
 */

public class SMLeagueCharts {

    public SMLeagueCharts() {
    }

    public List<AssistItem> assist;
    public List<GoalItem> goals;
    public List<CardItem> cards;

    public static class AssistItem{

        public AssistItem() {
        }

        public int assists, position, player_id, team_id, stage_id;
        public SMTeam team;
        public SMPlayer player;
    }


    public static class CardItem{

        public CardItem() {
        }

        public int yellowcards, redcards, position, player_id, team_id, stage_id;
        public SMTeam team;
        public SMPlayer player;
    }


    public static class GoalItem{

        public GoalItem() {
        }

        public int goals, penalty_goals, position, player_id, team_id, stage_id;
        public SMTeam team;
        public SMPlayer player;
    }



}
