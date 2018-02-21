package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/10/18.
 */

class SquadModel(private val firebaseDBInstance: FirebaseDatabase):SquadContract.SquadModel{

    var teamInfoRef: DatabaseReference? = null
    var teamInfoListener: ValueEventListener? = null

    var coachRef:DatabaseReference? = null
    var coachListener:ValueEventListener? = null

    override fun subscribeToSquad(listener: ValueEventListener) {
        this.teamInfoRef = firebaseDBInstance.getReference("/v2/team/squad")
        teamInfoRef?.keepSynced(true)
        this.teamInfoListener = listener
        teamInfoRef?.addValueEventListener(this.teamInfoListener)
    }

    override fun subscribeToCoach(listener: ValueEventListener) {
        this.coachRef = firebaseDBInstance.getReference("/v2/team/coach")
        coachRef?.keepSynced(true)
        this.coachListener = listener
        coachRef?.addValueEventListener(this.coachListener)
    }

    override fun unsubscribe() {
        if(this.teamInfoRef != null && this.teamInfoListener != null){
            teamInfoRef?.removeEventListener(teamInfoListener)
        }
        if(this.coachRef != null && this.coachListener != null){
            teamInfoRef?.removeEventListener(coachListener)
        }
    }
}