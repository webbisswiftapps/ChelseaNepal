
package com.webbisswift.cfcn.domain.model;


import java.util.List;

public class MatchFactsHolder {

    public Facts facts;
    public Lineup lineup;
    public MatchStats match_stats;
    public Substitutes subs;
    public List<MatchComment> comments;

    public Facts getFacts() {
        return facts;
    }

    public void setFacts(Facts facts) {
        this.facts = facts;
    }

    public Lineup getLineup() {
        return lineup;
    }

    public void setLineup(Lineup lineup) {
        this.lineup = lineup;
    }

    public MatchStats getMatchStats() {
        return match_stats;
    }

    public void setMatchStats(MatchStats match_stats) {
        this.match_stats = match_stats;
    }

    public Substitutes getSubs() {
        return subs;
    }

    public void setSubs(Substitutes subs) {
        this.subs = subs;
    }

    @Override
    public String toString() {
        return facts.toString();
    }
}
