package com.webbisswift.cfcn.domain.model.v2;

import java.util.List;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMComments {

    List<Comment> data;

    public SMComments() {
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public static class Comment{
        long fixture_id;
        boolean important;
        int order;
        boolean goal;
        int minute;
        int extra_minute;
        String comment;

        public Comment() {
        }

        public long getFixture_id() {
            return fixture_id;
        }

        public void setFixture_id(long fixture_id) {
            this.fixture_id = fixture_id;
        }

        public boolean isImportant() {
            return important;
        }

        public void setImportant(boolean important) {
            this.important = important;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public boolean isGoal() {
            return goal;
        }

        public void setGoal(boolean goal) {
            this.goal = goal;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getExtra_minute() {
            return extra_minute;
        }

        public void setExtra_minute(int extra_minute) {
            this.extra_minute = extra_minute;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
