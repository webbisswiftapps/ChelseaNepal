package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_out

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 2/10/18.
 */

class TransferOutModel(private val firebaseDBInstance: FirebaseDatabase): TransferOutContract.TransferOutModel{

    var teamInfoRef: DatabaseReference? = null
    var teamInfoListener: ValueEventListener? = null

    override fun subscribeToTxOut(listener: ValueEventListener) {
        this.teamInfoRef = firebaseDBInstance.getReference("/v2/team/transfers/out")
        teamInfoRef?.keepSynced(true)
        this.teamInfoListener = listener
        teamInfoRef?.addValueEventListener(this.teamInfoListener)
    }

    override fun unsubscribeFromTxOut()  {
        if(this.teamInfoRef != null && this.teamInfoListener != null){
            teamInfoRef?.removeEventListener(teamInfoListener)
        }

    }
}