package com.webbisswift.cfcnepal.ui.screens.fragments.home

import android.app.AlarmManager
import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.webbisswift.cfcnepal.background.AppAlarmManagement
import com.webbisswift.cfcnepal.background.receivers.LiveServiceAlarmReciever
import com.webbisswift.cfcnepal.domain.model.MatchEvent
import java.text.SimpleDateFormat


/**
 * Created by apple on 12/3/17.
 */

class HomeModel(private val firebaseDBInstance:FirebaseDatabase, val lbm:LocalBroadcastManager,val context: Context) : HomeContract.HomeModel, BroadcastReceiver(){


    var listener:HomeContract.LiveScoreListener? = null
    var nextMatchRef:DatabaseReference? = null
    var nextMatchListener:ValueEventListener? = null
    var eplStatsRef:DatabaseReference? = null
    var eplStatsListener:ValueEventListener? = null
    var lastMatchRef:DatabaseReference? = null
    var lastMatchListener:ValueEventListener? = null

    override fun subscribeToNextMatch(listener:ValueEventListener) {
        nextMatchRef = firebaseDBInstance.getReference("next-match")
        nextMatchRef?.keepSynced(true)
        this.nextMatchListener = listener
        nextMatchRef?.addValueEventListener(nextMatchListener)
    }

    override fun subscribeToLastMatch(listener:ValueEventListener) {
        this.lastMatchRef = firebaseDBInstance.getReference("last-match")
        lastMatchRef?.keepSynced(true)
        this.lastMatchListener = listener
        lastMatchRef?.addValueEventListener(this.lastMatchListener)
    }



    override fun subscribeToEPLStats(listener: ValueEventListener) {
        this.eplStatsRef = firebaseDBInstance.getReference("tables/epl")
        eplStatsRef?.keepSynced(true)
        this.eplStatsListener = listener
        eplStatsRef?.addValueEventListener(this.eplStatsListener)
    }



    override fun setNextMatchAlarm(startDateTime: Date) {
        AppAlarmManagement(context).setNextMatchAlarm(startDateTime)
    }


    override fun subscribeToLive(listener: HomeContract.LiveScoreListener) {
        this.listener = listener
        lbm.registerReceiver(this, IntentFilter("LIVE_SCORE_EVENT"))
    }

    override fun unsubscribeFromLive() {
        lbm.unregisterReceiver(this)
    }

    override fun unsubscribeFromFirebase() {
        if(this.nextMatchRef != null && this.nextMatchListener != null){
            nextMatchRef?.removeEventListener(nextMatchListener)
        }

        if(this.lastMatchRef != null && this.lastMatchListener != null){
            lastMatchRef?.removeEventListener(lastMatchListener)
        }

        if(this.eplStatsRef != null && this.eplStatsListener != null){
            eplStatsRef?.removeEventListener(eplStatsListener)
        }


    }

    /* Broadcast Receiver */
    override fun onReceive(p0: Context?, intent: Intent?) {
        val status = intent?.getStringExtra("STATUS")!!
        val homeScr = intent.getLongExtra("HOME_SCORE",0)
        val awayScr = intent.getLongExtra("AWAY_SCORE", 0)
        val events = intent.getParcelableArrayListExtra<MatchEvent>("EVENTS")!!

        if(listener != null){
            listener?.scoreUpdateEvent(homeScr.toString(), awayScr.toString(), status, events)
        }
    }
}