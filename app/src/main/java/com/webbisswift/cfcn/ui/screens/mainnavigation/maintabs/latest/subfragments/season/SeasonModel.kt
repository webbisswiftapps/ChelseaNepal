package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.season

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by apple on 12/14/17.
 */

class SeasonModel(private val firebaseDBInstance: FirebaseDatabase) : SeasonContract.SeasonModel{

    var formGuideRef:DatabaseReference? = null
    var formGuideListener:ValueEventListener? = null
    var eplStatsRef:DatabaseReference? = null
    var eplStatsListener:ValueEventListener? = null
    var squadRef:DatabaseReference?=null
    var sqadListener:ValueEventListener? = null


    override fun subscribeToFormGuide(listener: ValueEventListener) {
        this.formGuideRef = firebaseDBInstance.getReference("v2/team-form")
        formGuideRef?.keepSynced(true)
        this.formGuideListener = listener
        formGuideRef?.addValueEventListener(formGuideListener!!)
    }



    override fun subscribeToLeagueTable(listener: ValueEventListener) {
        this.eplStatsRef = firebaseDBInstance.getReference("v2/leagues/8/standings")
        eplStatsRef?.keepSynced(true)
        this.eplStatsListener = listener
        eplStatsRef?.addValueEventListener(this.eplStatsListener!!)
    }

    override fun subscribeToSquad(listener: ValueEventListener) {
        this.squadRef = firebaseDBInstance.getReference("v2/team/squad")
        squadRef?.keepSynced(true)
        this.sqadListener = listener
        squadRef?.addValueEventListener(this.sqadListener!!)
    }

    override fun unsubscribeFromFirebase() {


        if(this.formGuideRef != null && this.formGuideListener != null){
            formGuideRef?.removeEventListener(formGuideListener!!)
        }

        if(this.eplStatsRef != null && this.eplStatsListener != null){
            eplStatsRef?.removeEventListener(eplStatsListener!!)
        }

        if(this.squadRef != null && this.sqadListener != null){
            squadRef?.removeEventListener(sqadListener!!)
        }


    }



}