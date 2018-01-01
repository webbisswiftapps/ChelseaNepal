package com.webbisswift.cfcn.ui.screens.match_center

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/31/17.
 */

class MatchCenterModel(private val firebaseDBInstance: FirebaseDatabase){

    var liveMatchRef: DatabaseReference? = null
    var liveMatchListener: ValueEventListener? = null

    fun subscribeToNextMatch(listener: ValueEventListener) {
        this.liveMatchRef = firebaseDBInstance.getReference("next-match")
        liveMatchRef?.keepSynced(true)
        this.liveMatchListener = listener
        liveMatchRef?.addValueEventListener(this.liveMatchListener)
    }

    fun unsubscribeFromNextMatch() {
        if(this.liveMatchRef != null && this.liveMatchListener != null){
            liveMatchRef?.removeEventListener(liveMatchListener)
        }

    }

}