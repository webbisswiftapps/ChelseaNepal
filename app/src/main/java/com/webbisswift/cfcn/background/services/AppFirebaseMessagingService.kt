package com.webbisswift.cfcn.background.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.webbisswift.cfcn.background.AppAlarmManagement
import java.text.SimpleDateFormat
import java.util.*
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.webbisswift.cfcn.ui.widgets.NextMatchWidget


/**
 * Created by apple on 12/12/17.
 */

class AppFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage?) {

        val from = message?.from
        if(from?.contentEquals("/topics/NextMatchTopic")!!) {
            parseNextMatchUpdatePush(message.data)
        }else if(from.contentEquals("/topics/NewsUpdatePing"))
            startNewsUpdateService(message.data)
    }


    /* Next Match Push Handlers */

    fun parseNextMatchUpdatePush(data:Map<String, String>?){
        updateNextMatchWidget()
        val type:String? = data?.get("type")

        if(type != null && type.contentEquals("next_match")){
            val startDate = data.get("match_start_date")
            val startTime = data.get("match_start_time")
            val home = data.get("match_home")
            val away = data.get("match_away")

            if(startDate!=null && startTime!=null && home!=null && away!=null){
                val startDT = startDate+" "+startTime
                setNextMatchAlarm(startDT, home, away)
            }
        }
    }

    fun setNextMatchAlarm(startDT: String, home:String, away:String) {
        val formatter = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("Asia/Kathmandu")
        try {
            val startDateTime = formatter.parse(startDT)
            AppAlarmManagement(this).setNextMatchAlarm(startDateTime, home, away)

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("FirebaseMessaging","NextMatchAlarm |  Error: "+e.localizedMessage)
        }
    }




    /* News Update Push Handlers */
    fun startNewsUpdateService(data:Map<String, String>?){
        Log.d("FirebaseMessaging", "Received push to start News Update Service!")
        AppAlarmManagement(this).startNewsUpdateService(true)
    }


    fun updateNextMatchWidget(){
        //Also update widget
        val intent = Intent(this, NextMatchWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(application, NextMatchWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }


}