package com.webbisswift.cfcn.utils

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.app.NotificationChannel
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import com.webbisswift.cfcn.R


@TargetApi(26)
class NotificationUtils(val base:Context) : ContextWrapper(base) {

    private var mManager:NotificationManager? = null

    companion object {
        val news_notification_channel = "com.webbisswift.cfcn.notifications.NEWS_CHANNEL"
        val match_notification_channel = "com.webbisswift.cfcn.notifications.MATCH_EVENTS_CHANNEL"

    }



     fun createChannels(){
        // create android channel
        val androidChannel = NotificationChannel(news_notification_channel,
                "CFCN News Notifications", NotificationManager.IMPORTANCE_DEFAULT)
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true)
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true)
        // Sets the notification light color for notifications posted to this channel
        androidChannel.lightColor = Color.BLUE
         val audioAttributes = AudioAttributes.Builder()
                 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                 .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                 .build()
        androidChannel.setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated), audioAttributes)



        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        getManager().createNotificationChannel(androidChannel)

        // create ios channel
        val iosChannel = NotificationChannel(match_notification_channel,
                "CFCN Match Events Notifications", NotificationManager.IMPORTANCE_HIGH)
        iosChannel.enableLights(true)
        iosChannel.enableVibration(true)
        iosChannel.lightColor = Color.BLUE
        iosChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
         val audioAttributes1 = AudioAttributes.Builder()
                 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                 .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                 .build()
         iosChannel.setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated), audioAttributes1)
        getManager().createNotificationChannel(iosChannel)

    }


    private fun getManager():NotificationManager{
        if(mManager == null){
            mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        return mManager!!
    }


}