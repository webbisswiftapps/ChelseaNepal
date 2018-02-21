package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/12/18.
 */

public class SMLeagueCoverage {
    boolean topscorer_cards, topscorer_assists, topscorer_goals;

    public SMLeagueCoverage() {
    }

    public boolean isTopscorer_cards() {
        return topscorer_cards;
    }

    public void setTopscorer_cards(boolean topscorer_cards) {
        this.topscorer_cards = topscorer_cards;
    }

    public boolean isTopscorer_assists() {
        return topscorer_assists;
    }

    public void setTopscorer_assists(boolean topscorer_assists) {
        this.topscorer_assists = topscorer_assists;
    }

    public boolean isTopscorer_goals() {
        return topscorer_goals;
    }

    public void setTopscorer_goals(boolean topscorer_goals) {
        this.topscorer_goals = topscorer_goals;
    }

    public boolean showLeaderboard(){
        return topscorer_cards && topscorer_assists && topscorer_goals;
    }
}
