package com.webbisswift.cfcnepal.background.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.webbisswift.cfcnepal.background.AppAlarmManagement
import com.webbisswift.cfcnepal.background.receivers.LiveServiceAlarmReciever
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by apple on 12/12/17.
 */

class AppFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage?) {
        Log.d("FirebaseMessaging", "Message Received: "+message?.data +" "+message?.from)

        val from = message?.from
        if(from?.contentEquals("/topics/NextMatchTopic")!!)
            parseNextMatchUpdatePush(message?.data)
        else if(from?.contentEquals("/topics/NewsUpdatePing"))
            startNewsUpdateService(message?.data)

    }


    /* Next Match Push Handlers */

    fun parseNextMatchUpdatePush(data:Map<String, String>?){
        val type:String? = data?.get("type")

        if(type != null && type.contentEquals("next_match")){
            val startDate = data?.get("match_start_date")
            val startTime = data?.get("match_start_time")

            if(startDate!=null && startTime!=null){
                val startDT = startDate+" "+startTime
                setNextMatchAlarm(startDT)
            }
        }
    }

    fun setNextMatchAlarm(startDT: String) {
        val formatter = SimpleDateFormat("EEEE, dd MMM yyyy HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("Asia/Kathmandu")
        try {
            val startDateTime = formatter.parse(startDT)
            AppAlarmManagement(this).setNextMatchAlarm(startDateTime)

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


}