package com.webbisswift.cfcn.background.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.webbisswift.cfcn.R;
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper;
import com.webbisswift.cfcn.ui.screens.home.MainActivity;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Created by apple on 12/9/17.
 */

public class LiveServiceAlarmReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NextMatchAlarmReceiver","Alarm Received.. Notify User if necessary.");

        SettingsHelper settings = new SettingsHelper(context);
        if(!settings.shouldShowMatchAlert())  return;

        try {
            //give notification if necessary
            String home = intent.getStringExtra("HOME");
            String away = intent.getStringExtra("AWAY");
            // String date = intent.getStringExtra("DATE");

            if (home != null && away != null) {
                showNotification(context, home, away);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void showNotification(Context c, String home , String away){
        String title = home+" vs "+away;
        String  message = "This match will start shortly.";

        Intent i = new Intent(c, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(c, 407, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "NextMatchAlarmReceiver")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pi)
                .setSound(Uri.parse("android.resource://" + c.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000);

        NotificationManager mgr = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.notify(4007, builder.build());

    }
}
