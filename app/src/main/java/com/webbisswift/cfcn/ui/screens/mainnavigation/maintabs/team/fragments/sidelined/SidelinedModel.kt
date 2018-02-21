package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/10/18.
 */

class SidelinedModel(private val firebaseDBInstance: FirebaseDatabase):SidelinedContract.SidelinedModel{

    var teamInfoRef: DatabaseReference? = null
    var teamInfoListener: ValueEventListener? = null

    override fun subscribeToSidelined(listener: ValueEventListener) {
        this.teamInfoRef = firebaseDBInstance.getReference("/v2/team/sidelined")
        teamInfoRef?.keepSynced(true)
        this.teamInfoListener = listener
        teamInfoRef?.addValueEventListener(this.teamInfoListener)
    }

    override fun unsubscribeFromSidelined() {
        if(this.teamInfoRef != null && this.teamInfoListener != null){
            teamInfoRef?.removeEventListener(teamInfoListener)
        }

    }
}