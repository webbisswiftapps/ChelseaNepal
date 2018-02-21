package com.webbisswift.cfcn.domain.model.v2;


/**
 * Created by apple on 2/12/18.
 */

public class SMTeamLeagueStats {

    public HATStatsString avg_first_goal_conceded, avg_first_goal_scored;
    public HATStatsFloat avg_goals_per_game_conceded, avg_goals_per_game_scored;
    public HATStatsInt clean_sheet, draw, failed_to_score, goals_against, goals_for, lost, win;

    //public List<List<ScoringPeriod>> scoring_minutes;

    public SMTeamLeagueStats() {
    }

    public static class HATStatsString{
        public String home, away, total;

        public HATStatsString() {
        }
    }

    public static class HATStatsFloat{
        public float home, away, total;

        public HATStatsFloat() {
        }
    }

    public static class HATStatsInt{
        public int home, away, total;

        public HATStatsInt() {
        }
    }

    public static class ScoringPeriod{
        public int count;
        public String minute, percentage;

        public ScoringPeriod() {
        }
    }


}
