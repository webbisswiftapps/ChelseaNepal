package com.webbisswift.cfcn.ui.screens.modal.compdetails.overview

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/12/18.
 */

class CompOverviewModel(private val firebaseDBInstance: FirebaseDatabase, val leagueId:Int): CompOverviewContract.CompOverviewModel{

    var matchesRef: DatabaseReference? = null
    var matchesListener: ValueEventListener? = null
    var standingRef: DatabaseReference? = null
    var standingListener: ValueEventListener? = null




    override fun subscribeToMatches(listener: ValueEventListener) {
        this.matchesRef = firebaseDBInstance.getReference(String.format("/v2/leagues/%d/matches", leagueId))
        matchesRef?.keepSynced(true)
        this.matchesListener = listener
        matchesRef?.addValueEventListener(this.matchesListener)
    }

    override fun subscribeToStandings(listener: ValueEventListener) {
        this.matchesRef = firebaseDBInstance.getReference(String.format("/v2/leagues/%d/standings", leagueId))
        matchesRef?.keepSynced(true)
        this.matchesListener = listener
        matchesRef?.addValueEventListener(this.matchesListener)
    }

    override fun unsubscribe() {
        if(this.standingRef != null && this.standingListener != null){
            standingRef?.removeEventListener(standingListener)
        }

        if(this.matchesRef != null && this.matchesListener != null){
            matchesRef?.removeEventListener(matchesListener)
        }
    }
}