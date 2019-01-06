package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/7/17.
 */


class MatchesModel(private val firebaseDBInstance: FirebaseDatabase):MatchesContract.MatchesModel{

    override fun subscribeToFixtures(listener:ValueEventListener) {
        val nextMatchRef: DatabaseReference = firebaseDBInstance.getReference("v2/fixtures")
        nextMatchRef.keepSynced(true)
        nextMatchRef.addValueEventListener(listener)
    }

    override fun subscribeToResults(listener: ValueEventListener) {
        val nextMatchRef: DatabaseReference = firebaseDBInstance.getReference("v2/results")
        nextMatchRef.keepSynced(true)
        nextMatchRef.addValueEventListener(listener)
    }

}