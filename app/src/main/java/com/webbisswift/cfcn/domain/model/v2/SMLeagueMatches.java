package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/12/18.
 */

public class SMLeagueMatches {

    SMMatch last, next;

    public SMLeagueMatches() {
    }

    public SMMatch getLast() {
        return last;
    }

    public void setLast(SMMatch last) {
        this.last = last;
    }

    public SMMatch getNext() {
        return next;
    }

    public void setNext(SMMatch next) {
        this.next = next;
    }
}
