package com.webbisswift.cfcnepal.domain.model;

import java.util.ArrayList;

/**
 * Created by apple on 12/6/17.
 */

public class LeagueTableItem {

    public String info, path, shirt, team, teamfull;
    public int d, ga, gd, gf, l, p, pts, pos, w;
    public ArrayList<String> form;

    public String getInfo() {
        return info;
    }

    public String getPath() {
        return path;
    }

    public String getShirt() {
        return shirt;
    }

    public String getTeam() {
        return team;
    }

    public String getTeamfull() {
        return teamfull;
    }

    public String getDraws() {
        return String.valueOf(d);
    }

    public String getGoalsAgainst() {
        return String.valueOf(ga);
    }

    public String getGoalDifference() {
        return String.valueOf(gd);
    }

    public String getGoalsFor() {
        return String.valueOf(gf);
    }

    public String getLosses() {
        return String.valueOf(l);
    }

    public String getMatchesPlayed() {
        return String.valueOf(p);
    }

    public String getPts() {
        return String.valueOf(pts);
    }

    public String getPosition() {
        return String.valueOf(pos);
    }

    public String getWins() {
        return String.valueOf(w);
    }

    public ArrayList<String> getForm() {
        return form;
    }
}
