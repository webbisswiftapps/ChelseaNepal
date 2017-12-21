package com.webbisswift.cfcn.ui.screens.match_facts

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/20/17.
 */

class MatchFactsModel(private val firebaseDBInstance: FirebaseDatabase):MatchFactsContract.MatchFactsModel{

    var lastMatchRef: DatabaseReference? = null
    var lastMatchListener:ValueEventListener? = null

    override fun subscribeToLastMatch(listener:ValueEventListener) {
        this.lastMatchRef = firebaseDBInstance.getReference("last-match")
        lastMatchRef?.keepSynced(true)
        this.lastMatchListener = listener
        lastMatchRef?.addValueEventListener(this.lastMatchListener)
    }

    override fun unsubscribeFromFirebase() {
        if(this.lastMatchRef != null && this.lastMatchListener != null){
            lastMatchRef?.removeEventListener(lastMatchListener)
        }

    }

}