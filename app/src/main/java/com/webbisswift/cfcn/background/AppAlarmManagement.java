package com.webbisswift.cfcn.background;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webbisswift.cfcn.background.receivers.LiveServiceAlarmReciever;
import com.webbisswift.cfcn.background.services.NewsUpdateJobService;
import com.webbisswift.cfcn.background.services.NewsUpdateService;
import com.webbisswift.cfcn.domain.model.Match;

import java.util.Calendar;
import java.util.Date;

import kotlin.Suppress;

/**
 * Created by apple on 12/12/17.
 */

public class AppAlarmManagement {

    private Context c;
   public AppAlarmManagement(Context c){
       this.c = c;
   }



   public void startNewsUpdateService(boolean shouldNotify){
      if(Build.VERSION.SDK_INT >= 26)
          scheduleNewsUpdateWithJobScheduler(shouldNotify);
      else startNewsUpdateServiceImmediate(shouldNotify);
   }

   @TargetApi(Build.VERSION_CODES.O)
   private void scheduleNewsUpdateWithJobScheduler(boolean shouldNotify){
           ComponentName serviceComponent = new ComponentName(c, NewsUpdateJobService.class);
           JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
           builder.setOverrideDeadline(15000); // maximum delay
           builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // update only when the user has wifi
           JobScheduler jobScheduler = c.getSystemService(JobScheduler.class);
           jobScheduler.schedule(builder.build());
            Log.d("AppAlarmManagement","Started News Update Service in JOB");

   }


   private void startNewsUpdateServiceImmediate(boolean shouldNotify){
       try {
           Intent i = new Intent(c, NewsUpdateService.class);
           i.putExtra("SHOULD_NOTIFY", shouldNotify);
           Log.d("AppAlarmManagement","Started News Update Service");
           c.startService(i);
       }catch (Exception e){
           e.printStackTrace();
           Log.d("AppAlarmManagement","Failed to start news update service..");
       }
   }


   public void fetchNextMatchDetailsAndSetAlarm(){

       FirebaseDatabase fDB = FirebaseDatabase.getInstance();
       DatabaseReference nextMatchRef = fDB.getReference("next-match");
       nextMatchRef.keepSynced(true);
       nextMatchRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Match nextMatchInfo = dataSnapshot.getValue(Match.class);

               if(nextMatchInfo != null){
                   Date startDateTime = nextMatchInfo.getStartDateTime();
                   setNextMatchAlarm(startDateTime, nextMatchInfo.home, nextMatchInfo.away);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
                Log.d("AppAlarmManagement","FetchNextMatchDetailsAndSetAlarm | Error: "+databaseError.getMessage());
           }
       });

   }

   public void setNextMatchAlarm(Date  startDate, String homeTeam, String awayTeam){

           try {
               AlarmManager am = (AlarmManager) this.c.getSystemService(Context.ALARM_SERVICE);
               Calendar calendar = Calendar.getInstance();
               calendar.setTime(startDate);
               calendar.add(Calendar.MINUTE, -15);

               if(calendar.getTime().before(new Date())) {
                   Log.d("AppAlarmManagement","Trying to set next match alarm to a past date. Ignore.");
                   return;
               }

               /*calendar.setTime(new Date());
               calendar.add(Calendar.MINUTE, 1);*/

               Intent i = new Intent(this.c, LiveServiceAlarmReciever.class);
               i.putExtra("HOME", homeTeam);
               i.putExtra("AWAY", awayTeam);
               i.putExtra("DATE", startDate.toString());

               PendingIntent pi = PendingIntent.getBroadcast(c, 888, i, PendingIntent.FLAG_UPDATE_CURRENT);
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                   am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
               } else {
                   am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
   }



}
