package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/9/18.
 */

public class SMStandingItem {
    int position, points, team_id;
    String recent_form, result, status, team_name, group_name;

    SMStandingStats away, home, overall;
    SMStandingTotal total;

    public SMStandingItem() {
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getRecent_form() {
        return recent_form;
    }

    public void setRecent_form(String recent_form) {
        this.recent_form = recent_form;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public SMStandingStats getAway() {
        return away;
    }

    public void setAway(SMStandingStats away) {
        this.away = away;
    }

    public SMStandingStats getHome() {
        return home;
    }

    public void setHome(SMStandingStats home) {
        this.home = home;
    }

    public SMStandingStats getOverall() {
        return overall;
    }

    public void setOverall(SMStandingStats overall) {
        this.overall = overall;
    }

    public SMStandingTotal getTotal() {
        return total;
    }

    public void setTotal(SMStandingTotal total) {
        this.total = total;
    }

    public static class SMStandingStats{
        int draw, games_played, goals_against, goals_scored, lost, won;

        public SMStandingStats() {
        }

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }

        public int getGames_played() {
            return games_played;
        }

        public void setGames_played(int games_played) {
            this.games_played = games_played;
        }

        public int getGoals_against() {
            return goals_against;
        }

        public void setGoals_against(int goals_against) {
            this.goals_against = goals_against;
        }

        public int getGoals_scored() {
            return goals_scored;
        }

        public void setGoals_scored(int goals_scored) {
            this.goals_scored = goals_scored;
        }

        public int getLost() {
            return lost;
        }

        public void setLost(int lost) {
            this.lost = lost;
        }

        public int getWon() {
            return won;
        }

        public void setWon(int won) {
            this.won = won;
        }


    }

    public static class SMStandingTotal{
        String goal_difference;
        int points;

        public SMStandingTotal() {
        }

        public String getGoal_difference() {
            return goal_difference;
        }

        public void setGoal_difference(String goal_difference) {
            this.goal_difference = goal_difference;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }


}
