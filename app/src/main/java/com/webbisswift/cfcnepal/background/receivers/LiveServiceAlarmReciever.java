package com.webbisswift.cfcnepal.background.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.webbisswift.cfcnepal.background.services.LiveScoreService;

/**
 * Created by apple on 12/9/17.
 */

public class LiveServiceAlarmReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //start service
        Log.d("LSAlarm","Alarm Received.. Starting service");
        Intent i = new Intent(context, LiveScoreService.class);
        context.startService(i);
    }
}
