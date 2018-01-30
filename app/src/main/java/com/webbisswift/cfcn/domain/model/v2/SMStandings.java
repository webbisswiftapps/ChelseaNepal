package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMStandings {

    public SMStandings() {
    }

    int localteam_position , visitorteam_position;

    public int getLocalteam_position() {
        return localteam_position;
    }

    public void setLocalteam_position(int localteam_position) {
        this.localteam_position = localteam_position;
    }

    public int getVisitorteam_position() {
        return visitorteam_position;
    }

    public void setVisitorteam_position(int visitorteam_position) {
        this.visitorteam_position = visitorteam_position;
    }
}
