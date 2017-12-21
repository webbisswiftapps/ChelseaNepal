package com.webbisswift.cfcn.domain.model;

import java.util.List;

/**
 * Created by apple on 12/20/17.
 */

public class Lineup {

    List<LineupPlayer> localteam;
    List<LineupPlayer>  visitorteam;

    public List<LineupPlayer> getLocalteam() {
        return localteam;
    }

    public void setLocalteam(List<LineupPlayer> localteam) {
        this.localteam = localteam;
    }

    public List<LineupPlayer> getVisitorteam() {
        return visitorteam;
    }

    public void setVisitorteam(List<LineupPlayer> visitorteam) {
        this.visitorteam = visitorteam;
    }
}
