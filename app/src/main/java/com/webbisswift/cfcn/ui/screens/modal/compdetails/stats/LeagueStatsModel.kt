package com.webbisswift.cfcn.ui.screens.modal.compdetails.stats

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.ui.screens.modal.compdetails.leaderboard.LeaderboardContract

/**
 * Created by apple on 2/12/18.
 */


class LeagueStatsModel(private val firebaseDBInstance: FirebaseDatabase, val leagueId:Int): LeagueStatsContract.Model{

    var matchesRef: DatabaseReference? = null
    var matchesListener: ValueEventListener? = null





    override fun subscribeToStats(listener: ValueEventListener) {
        this.matchesRef = firebaseDBInstance.getReference(String.format("/v2/team/stats/%d", leagueId))
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