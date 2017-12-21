package com.webbisswift.cfcn.ui.screens.team

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.ui.screens.match_facts.MatchFactsContract

/**
 * Created by biswas on 21/12/2017.
 */


class TeamInfoModel(private val firebaseDBInstance: FirebaseDatabase){

    var teamInfoRef: DatabaseReference? = null
    var teamInfoListener: ValueEventListener? = null

     fun subscribeToTeamInfo(listener: ValueEventListener) {
        this.teamInfoRef = firebaseDBInstance.getReference("team_info")
        teamInfoRef?.keepSynced(true)
        this.teamInfoListener = listener
        teamInfoRef?.addValueEventListener(this.teamInfoListener)
    }

     fun unsubscribeFromTeamInfo() {
        if(this.teamInfoRef != null && this.teamInfoListener != null){
            teamInfoRef?.removeEventListener(teamInfoListener)
        }

    }

}