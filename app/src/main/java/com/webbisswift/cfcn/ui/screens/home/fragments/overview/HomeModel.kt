package com.webbisswift.cfcn.ui.screens.home.fragments.overview

import android.appwidget.AppWidgetManager
import android.content.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import android.support.v4.content.LocalBroadcastManager
import com.webbisswift.cfcn.background.AppAlarmManagement
import com.webbisswift.cfcn.domain.model.MatchEvent
import com.webbisswift.cfcn.ui.widgets.NextMatchWidget


/**
 * Created by apple on 12/3/17.
 */

class HomeModel(private val firebaseDBInstance:FirebaseDatabase,val context: Context) : HomeContract.HomeModel{


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



    override fun setNextMatchAlarm(startDateTime: Date, home:String, away:String) {
        AppAlarmManagement(context).setNextMatchAlarm(startDateTime, home, away)
        //Also update widget
        val intent = Intent(context, NextMatchWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        val ids = AppWidgetManager.getInstance(context.applicationContext).getAppWidgetIds(ComponentName(context.applicationContext, NextMatchWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(intent)
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



}