package com.webbisswift.cfcn.ui.screens.modal.compdetails.leaderboard

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/12/18.
 */


class LeaderboardModel(private val firebaseDBInstance: FirebaseDatabase, val leagueId:Int): LeaderboardContract.Model{

    var matchesRef: DatabaseReference? = null
    var matchesListener: ValueEventListener? = null





    override fun subscribeToCharts(listener: ValueEventListener) {
        this.matchesRef = firebaseDBInstance.getReference(String.format("/v2/leagues/%d/charts", leagueId))
        matchesRef?.keepSynced(true)
        this.matchesListener = listener
        matchesRef?.addValueEventListener(this.matchesListener)
    }

    override fun unsubscribe() {

        if(this.matchesRef != null && this.matchesListener != null){
            matchesRef?.removeEventListener(matchesListener)
        }
    }
}