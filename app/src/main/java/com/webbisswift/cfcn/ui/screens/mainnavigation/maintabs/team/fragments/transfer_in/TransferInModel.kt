package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/10/18.
 */

class TransferInModel(private val firebaseDBInstance: FirebaseDatabase): TransferInContract.TransferInModel{

    var teamInfoRef: DatabaseReference? = null
    var teamInfoListener: ValueEventListener? = null

    override fun subscribeToTxIn(listener: ValueEventListener) {
        this.teamInfoRef = firebaseDBInstance.getReference("/v2/team/transfers/in")
        teamInfoRef?.keepSynced(true)
        this.teamInfoListener = listener
        teamInfoRef?.addValueEventListener(this.teamInfoListener)
    }

    override fun unsubscribeFromTxIn()  {
        if(this.teamInfoRef != null && this.teamInfoListener != null){
            teamInfoRef?.removeEventListener(teamInfoListener)
        }

    }
}