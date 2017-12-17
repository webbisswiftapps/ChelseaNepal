package com.webbisswift.cfcnepal.ui.screens.fragments.season

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/14/17.
 */

class SeasonModel(private val firebaseDBInstance: FirebaseDatabase) : SeasonContract.SeasonModel{

    var cheTopScorersRef: DatabaseReference? = null
    var cheTopScorersListener:ValueEventListener? = null
    var formGuideRef:DatabaseReference? = null
    var formGuideListener:ValueEventListener? = null
    var eplStatsRef:DatabaseReference? = null
    var eplStatsListener:ValueEventListener? = null

    override fun subscribeToFormGuide(listener: ValueEventListener) {
        this.formGuideRef = firebaseDBInstance.getReference("team-form")
        formGuideRef?.keepSynced(true)
        this.formGuideListener = listener
        formGuideRef?.addValueEventListener(formGuideListener)
    }


    override fun subscribeToTopScorers(listener: ValueEventListener) {
        this.cheTopScorersRef = firebaseDBInstance.getReference("top-scorers/che")
        cheTopScorersRef?.keepSynced(true)
        this.cheTopScorersListener = listener
        cheTopScorersRef?.addValueEventListener(this.cheTopScorersListener)
    }

    override fun subscribeToLeagueTable(listener: ValueEventListener) {
        this.eplStatsRef = firebaseDBInstance.getReference("tables/epl")
        eplStatsRef?.keepSynced(true)
        this.eplStatsListener = listener
        eplStatsRef?.addValueEventListener(this.eplStatsListener)

    }



    override fun unsubscribeFromFirebase() {

        if(this.cheTopScorersRef != null && this.cheTopScorersListener != null){
            cheTopScorersRef?.removeEventListener(cheTopScorersListener)
        }

        if(this.formGuideRef != null && this.formGuideListener != null){
            formGuideRef?.removeEventListener(formGuideListener)
        }

        if(this.eplStatsRef != null && this.eplStatsListener != null){
            eplStatsRef?.removeEventListener(eplStatsListener)
        }


    }



}