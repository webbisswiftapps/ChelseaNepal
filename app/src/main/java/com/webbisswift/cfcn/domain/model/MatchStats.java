package com.webbisswift.cfcn.domain.model;

import java.util.List;

/**
 * Created by apple on 12/20/17.
 */

public class MatchStats {
    List<MatchStat> localteam;
    List<MatchStat> visitorteam;

    public MatchStat getLocalteam() {
        return localteam.get(0);
    }

    public void setLocalteam(List<MatchStat> localteam) {
        this.localteam = localteam;
    }

    public MatchStat getVisitorteam() {
        return visitorteam.get(0);
    }

    public void setVisitorteam(List<MatchStat> visitorteam) {
        this.visitorteam = visitorteam;
    }
}
