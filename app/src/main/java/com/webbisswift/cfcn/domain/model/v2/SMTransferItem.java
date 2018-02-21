package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by apple on 2/10/18.
 */

public class SMTransferItem {

    String amount, date, transfer, type;
    int from_team_id, to_team_id;

    SMPlayer player;
    SMTeam team;

    public SMTransferItem() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrom_team_id() {
        return from_team_id;
    }

    public void setFrom_team_id(int from_team_id) {
        this.from_team_id = from_team_id;
    }

    public int getTo_team_id() {
        return to_team_id;
    }

    public void setTo_team_id(int to_team_id) {
        this.to_team_id = to_team_id;
    }

    public SMPlayer getPlayer() {
        return player;
    }

    public void setPlayer(SMPlayer player) {
        this.player = player;
    }

    public SMTeam getTeam() {
        return team;
    }

    public void setTeam(SMTeam team) {
        this.team = team;
    }
}
