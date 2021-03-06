package com.webbisswift.cfcn.background.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.webbisswift.cfcn.background.services.LiveScoreService;
import com.webbisswift.cfcn.domain.sharedpref.LiveUpdateScoreManager;

/**
 * Created by apple on 12/9/17.
 */

public class LiveServiceAlarmReciever extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //start service
        Log.d("LSAlarm","Alarm Received.. Starting service");
        Intent i = new Intent(context, LiveScoreService.class);
        context.startService(i);
    }
}
