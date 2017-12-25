package com.webbisswift.cfcn.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webbisswift.cfcn.background.receivers.LiveServiceAlarmReciever;
import com.webbisswift.cfcn.background.services.NewsUpdateService;
import com.webbisswift.cfcn.domain.model.Match;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 12/12/17.
 */

public class AppAlarmManagement {

    Context c;
   public AppAlarmManagement(Context c){
       this.c = c;
   }



   public void startNewsUpdateService(boolean shouldNotify){
       Intent i = new Intent(c, NewsUpdateService.class);
       i.putExtra("SHOULD_NOTIFY", shouldNotify);
       c.startService(i);
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
                   setNextMatchAlarm(startDateTime);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
                Log.d("AppAlarmManagement","FetchNextMatchDetailsAndSetAlarm | Error: "+databaseError.getMessage());
           }
       });

   }

   public void setNextMatchAlarm(Date  startDate){

       if(!wasNextMatchAlarmSet()) {


           try {
               AlarmManager am = (AlarmManager) this.c.getSystemService(Context.ALARM_SERVICE);
               Calendar calendar = Calendar.getInstance();
               calendar.setTime(startDate);
               calendar.add(Calendar.MINUTE , 1); //* set alarm after 1 minutos.

               Intent i = new Intent(this.c, LiveServiceAlarmReciever.class);
               PendingIntent pi = PendingIntent.getBroadcast(c, 888, i, PendingIntent.FLAG_UPDATE_CURRENT);
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                   am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                   Log.d("AppAlarmManagement", "Next Match Alarm | Set at: " + calendar.getTime().toString() + " Set exact.");
               } else {
                   am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                   Log.d("AppAlarmManagement", "Next Match Alarm | Set at: " + calendar.getTime().toString() + "Set not exact.");
               }
           } catch (Exception e) {
               e.printStackTrace();
               Log.d("AppAlarmManagement", "Next Match Alarm | Cannot set to: " + startDate);
           }
       }else{
           Log.d("AppAlarmManagement","Next match alarm was already set.");
       }

   }




   private boolean wasNextMatchAlarmSet(){
       Intent intent = new Intent(this.c, LiveServiceAlarmReciever.class);
       PendingIntent pi = PendingIntent.getBroadcast(c, 888, intent, PendingIntent.FLAG_NO_CREATE);
       return pi != null;
   }

}
