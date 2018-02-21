package com.webbisswift.cfcn.background.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.util.Log
import android.widget.RemoteViews
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.widgets.NextMatchWidget
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import android.app.PendingIntent
import com.webbisswift.cfcn.ui.screens.mainnavigation.MainNavigationActivity


/**
 * Created by apple on 12/26/17.
 */

class NextMatchWidgetUpdateService: Service(), ValueEventListener{

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

     var appWidgetId:Int? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Get NextMatch Info from Firebase

        appWidgetId = intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)



        if(appWidgetId != null) {
            Log.d("NextMatchWidget", "Setting listener to firebase..")
            val firebaseDBInstance = FirebaseDatabase.getInstance()
            val nextMatchRef = firebaseDBInstance.getReference("next-match")
            nextMatchRef?.addListenerForSingleValueEvent(this)
        }

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onCancelled(p0: DatabaseError?) {
        Log.d("NextMatchWidget", "Firebase DB Error: "+p0.toString())
    }

    override fun onDataChange(p0: DataSnapshot?) {

        try {
            val nextMatchInfo = p0?.getValue<Match>(Match::class.java)
            updateWidget(nextMatchInfo)
        }catch(e:Exception){
            e.printStackTrace()
            Log.d("NextMatchWidget", "onDataChange: Snapshot serialization error: "+e.toString())
        }
    }


    private fun updateWidget(match: Match?){



        if(match != null) {

            val view = RemoteViews(packageName, R.layout.next_match_widget)

            val homeIntent = Intent(this, MainNavigationActivity::class.java)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            val homePendingIntent = PendingIntent.getActivity(this, 0, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            view.setOnClickPendingIntent(R.id.nextMatchWidget, homePendingIntent)

            var logo = ""
            if(!match.away.toLowerCase().contentEquals("chelsea")) {
                view.setTextViewText(R.id.nextMatchAwayTeam, match.awayfull)
                logo = match.awayShirtURL
            }else{
                view.setTextViewText(R.id.nextMatchAwayTeam, match.homefull)
                logo = match.homeShirtURL
            }

            view.setTextViewText(R.id.nextMatchCompetition, match.competition)
            view.setTextViewText(R.id.nextmatchTVGuide, match.tv_guide)
            view.setTextViewText(R.id.nextMatchTimings, match.start_date+" "+match.start_time)

            val appWidgetTarget = object : AppWidgetTarget(this, R.id.awayTeamLogo, view, appWidgetId!!) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                    super.onResourceReady(resource, transition)
                }
            }

            Glide
                    .with(applicationContext)
                    .asBitmap()
                    .load(logo)
                    .into(appWidgetTarget)


            val theWidget = ComponentName(this, NextMatchWidget::class.java)
            val manager = AppWidgetManager.getInstance(this)
            manager.updateAppWidget(theWidget, view)
            Log.d("NextMatchWidget", "Widgets updated.")

        }
    }
}