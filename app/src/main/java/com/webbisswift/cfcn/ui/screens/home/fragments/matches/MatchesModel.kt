package com.webbisswift.cfcn.ui.screens.home.fragments.matches

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/7/17.
 */


class MatchesModel(private val firebaseDBInstance: FirebaseDatabase):MatchesContract.MatchesModel{

    override fun subscribeToFixtures(listener:ValueEventListener) {
        val nextMatchRef: DatabaseReference = firebaseDBInstance.getReference("fixtures/schedule")
        nextMatchRef.keepSynced(true)
        nextMatchRef.addValueEventListener(listener)
    }

    override fun subscribeToResults(listener: ValueEventListener) {
        val nextMatchRef: DatabaseReference = firebaseDBInstance.getReference("results/matches")
        nextMatchRef.keepSynced(true)
        nextMatchRef.addValueEventListener(listener)
    }

}