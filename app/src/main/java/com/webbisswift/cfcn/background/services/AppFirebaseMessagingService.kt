package com.webbisswift.cfcn.background.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.webbisswift.cfcn.background.AppAlarmManagement
import java.text.SimpleDateFormat
import java.util.*
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.widgets.NextMatchWidget
import com.webbisswift.cfcn.utils.NotificationUtils
import com.webbisswift.cfcn.v3.ui.screens.mainnav.MainNavigationActivity
import org.jetbrains.anko.runOnUiThread



/**
 * Created by apple on 12/12/17.
 */

class AppFirebaseMessagingService : FirebaseMessagingService(){

    var notificationTarget:NotificationTarget? = null

    override fun onMessageReceived(message: RemoteMessage?) {

        val from = message?.from
        if(from?.contentEquals("/topics/NextMatchTopic")!!) {
            parseNextMatchUpdatePush(message.data)
        }else if(from.contentEquals("/topics/NewsUpdatePingv2")){
            readNewsNotification(message.data)
        }else if(from.contentEquals("/topics/v2CFCNEventsNotifications")){
            if(message.notification != null) {
                showCFCNEventNotification(message.notification!!)
            }
        }
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



    fun readNewsNotification(data:Map<String, String>){
        val title = data["title"]
        val image = data["image"]
        val link = data["link"]
        val author = data["author"]
        //val date = data["pubDate"]

        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.news_notification_layout)
        remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.lion_logo_ony)
        remoteViews.setImageViewResource(R.id.newsIcn, R.drawable.ic_news_rss_notification)
        remoteViews.setTextViewText(R.id.title, author+" | CFCN" )
        remoteViews.setTextViewText(R.id.desc, title?.trim())


        val notificationIntent = Intent(applicationContext, MainNavigationActivity::class.java)
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("NOTIFICATION_URL", link)
        val pendingIntent = PendingIntent.getActivity(this,  10001, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        val builder = NotificationCompat.Builder(this, NotificationUtils.news_notification_channel)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(title)
                .setContentText(title)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000)
                .setCustomBigContentView(remoteViews)
                .setAutoCancel(true)





        val notification = builder.build()
        val notficationMGR = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notficationMGR.notify(10001, notification)
        notificationTarget = NotificationTarget(
                applicationContext,
                R.id.imagenotileft,
                remoteViews,
                notification,
                10001)

        runOnUiThread {
            Glide.with(applicationContext).asBitmap().load(image).into(notificationTarget!!)
        }


    }


    fun showCFCNEventNotification(message: RemoteMessage.Notification){



        val notificationIntent = Intent(applicationContext, MainNavigationActivity::class.java)
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,  8080, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val text = message.body


        val builder = NotificationCompat.Builder(this, NotificationUtils.match_notification_channel)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(message.title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000)
                .setAutoCancel(true)


        val notification = builder.build()
        val notficationMGR = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notficationMGR.notify(8080, notification)
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