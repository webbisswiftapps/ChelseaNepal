package com.webbisswift.cfcn.background.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.webbisswift.cfcn.background.AppAlarmManagement

/**
 * Created by apple on 12/12/17.
 */

class DeviceBootReciever :BroadcastReceiver(){

    var appAM:AppAlarmManagement? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AppAlarmManagement", "Device boot received. Start App Alarm Management.")
         appAM = AppAlarmManagement(context)

         //1. Fetch Next Match Details from Firebase and set alarm
          appAM?.fetchNextMatchDetailsAndSetAlarm()

         //2. Start News Update Service
         appAM?.startNewsUpdateService(true)
    }


}