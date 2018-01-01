package com.webbisswift.cfcn.domain.model;

/**
 * Created by apple on 12/31/17.
 */

public class MatchComment {

    public String id, important, isgoal, comment, minute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getIsgoal() {
        return isgoal;
    }

    public void setIsgoal(String isgoal) {
        this.isgoal = isgoal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
