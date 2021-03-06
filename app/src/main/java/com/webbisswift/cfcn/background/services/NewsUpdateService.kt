package com.webbisswift.cfcn.background.services

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.localdb.AppDatabase
import com.webbisswift.cfcn.domain.localdb.dao.NewsDao
import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem
import com.webbisswift.cfcn.domain.model.NewsItem
import com.webbisswift.cfcn.domain.model.NewsResponse
import com.webbisswift.cfcn.domain.model.VideoItem
import com.webbisswift.cfcn.domain.model.VideoResponse
import com.webbisswift.cfcn.domain.net.Constants
import com.webbisswift.cfcn.domain.sharedpref.NewsUpdateManager
import com.webbisswift.cfcn.root.CFCNepalApp
import com.webbisswift.cfcn.ui.screens.webview.WebViewActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import kotlin.collections.ArrayList

/**
 * Created by apple on 12/12/17.
 */

class NewsUpdateService: IntentService("NewsUpdateService"), Response.ErrorListener{

    private val requestQueue = CFCNepalApp.instance?.requestQueue
    private var newsDAO:NewsDao? = null




    private var total = 0
    private val newNewsList:ArrayList<DBNewsItem> = ArrayList()
    private var oldNewsList:List<DBNewsItem> = ArrayList()
    private var potentialNotifications:ArrayList<NewsItem> = ArrayList()
    private var potentialVideoNotifications:ArrayList<VideoItem> = ArrayList()



    private val newsSources = arrayOf(Constants.GUARDIAN_FEED_URL, Constants.METRO_FEED_URL, Constants.DM_FEED_URL, Constants.CNEWS_FEED_URL)
    private val videoSources = arrayOf(Constants.CHELSEA_FC_URL, Constants.H100_PER_CHE_URL, Constants.SHPENDI_CFC_URL ,Constants.BEANYMAN_CHE_URL)

     var shouldNotify:Boolean = true

    override fun onHandleIntent(intent: Intent?) {

        Log.d("NewsUpdateService", "news update service started... updating news ...")
        //check news updates and then show notifications if necessary
        //first load old news items in the db
        newsDAO = AppDatabase.getInstance(this).newsDao()
        if(newsDAO != null ) {
            oldNewsList = newsDAO?.getAllNews()!!
        }


        if(intent != null) {
            this.shouldNotify = intent.getBooleanExtra("SHOULD_NOTIFY", true)
        }


        newNewsList.clear()
        total +=loadAllNews()
        total += loadYoutubeVideos()

    }

     private fun loadAllNews():Int {

        val newsReceiver = object: Response.Listener<JSONObject> {
            override fun onResponse(response: JSONObject?) {
                try {
                    val newsResponse = NewsResponse.parseResponse(response.toString())
                    if (newsResponse.query.count > 0) {
                        addNewsToCollection(newsResponse.query.results.item)
                    }else reduceCount()
                }catch (e:Exception){
                    e.printStackTrace()
                    reduceCount()
                }
            }
        }


        var totalLoading = 0
        for(source in newsSources) {
            val request = JsonObjectRequest(Request.Method.GET, source, null, newsReceiver, null)
            request.tag = this
            requestQueue?.add(request)
            totalLoading++
        }
        return totalLoading
    }

     private fun loadYoutubeVideos():Int{


        val newsReceiver = object: Response.Listener<JSONObject> {
            override fun onResponse(response: JSONObject?) {
                try {
                    val newsResponse = VideoResponse.parseResponse(response.toString())
                    if (newsResponse.query.count > 0) {
                        addVideosToCollection(newsResponse.query.results.item)
                    }else reduceCount()
                }catch(e:Exception){
                    Log.d("NewsUpdateService","Video Response: "+response.toString())
                    e.printStackTrace()
                    reduceCount()
                }
            }
        }


        var totalLoading = 0
        for(source in videoSources) {
            val request = JsonObjectRequest(Request.Method.GET, source, null, newsReceiver, null)
            request.tag = this
            requestQueue?.add(request)
            totalLoading++
        }

        return totalLoading
    }


    private fun addNewsToCollection(items:List<NewsItem>){
        var count = 0
        potentialNotifications.add(items[0])
        for(item in items){
            try {

                val isHighlighted = ( count % 10 == 0)
                val normalized = DBNewsItem(item.title, item.thumbURL, item.newsAuthor, item.pubDateString, item.hasVideo(), item.link, isHighlighted)
                newNewsList.add(normalized)
                count ++

            }catch(e:Exception){
                e.printStackTrace()
                Log.d("NewsUpdateService", item.title)
            }
        }
        reduceCount()
    }

    private fun addVideosToCollection(items:List<VideoItem>){
        var count = 0
        potentialVideoNotifications.add(items[0])
        for(item in items){
            try {
                val normalized = DBNewsItem(item.title, item.group.thumbnail.url, item.author.name, item.published, true, item.link.href, true)
                newNewsList.add(normalized)
                count ++

            }catch(e:Exception){
                e.printStackTrace()
                Log.d("NewsUpdateService", item.title)
            }
        }
        reduceCount()
    }


    override fun onErrorResponse(error: VolleyError?) {
        Log.d("NewsUpdateService", error?.localizedMessage)
        reduceCount()
    }


    private fun reduceCount(){
        this.total --

        if(total <= 0){

            //sort item and then add it to database

            doAsync {
                newNewsList.sortByDescending { it.getPubDate() }
                newsDAO?.deleteAllNews()
                for (item in newNewsList){
                    newsDAO?.insertNewsItem(item)
                }

                uiThread {
                    //now show notification
                    if(shouldNotify)
                        processNotifications()
                    else Log.d("NewsUpdateServcice","Notification was not requested.")
                    sendNewsUpdateBroadcast()
                }
            }



        }
    }



    private var notificationTarget:NotificationTarget?  = null

    private fun processNotifications(){

        //1st process news
        val updateManager = NewsUpdateManager(this)
        val lastNewsUpdateTime = updateManager.getLastNewsUpdatedTime()
        this.potentialNotifications.sortByDescending { it.pubDate }
        val itemToNotify = potentialNotifications[0]
        if(itemToNotify.pubDate.isAfter(lastNewsUpdateTime)){
            //there is a new news!
            addNotification(itemToNotify, 808, itemToNotify.title)
            updateManager.saveLastNotifiedNewsTime(itemToNotify.pubDate)
        }else Log.d("NewsUpdateService","Stale news, do not notify.")

        val lastUpdatedYTTime = updateManager.getLastNotifiedYoutubeTime()
        val vidToNotify = potentialVideoNotifications[0]
        if(vidToNotify.pubDate.isAfter(lastUpdatedYTTime)){
            addNotificationVideo(vidToNotify, 909, itemToNotify.title)
            updateManager.saveLastNotifiedYoutubeTime(vidToNotify.pubDate)
        }else Log.d("NewsUpdateService","Stale video, do not notify..")
    }

    private fun addNotification(item:NewsItem, notificationId:Int, title:String){
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.news_notification_layout)
        remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.lion_logo_ony)
        remoteViews.setTextViewText(R.id.title, item.title.trim() +" ("+item.newsAuthor+")")


        val notificationIntent = Intent(applicationContext, WebViewActivity::class.java)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("URL", item.link)
        val pendingIntent = PendingIntent.getActivity(this,  notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        val builder = NotificationCompat.Builder(this, "News_Update_Service")
                .setSmallIcon(R.drawable.lion_logo_ony)
                .setContentTitle(title)
                .setContentText(item.title)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000)
                .setCustomBigContentView(remoteViews)


        val notification = builder.build()
        val notficationMGR = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notficationMGR.notify(notificationId, notification)
        notificationTarget = NotificationTarget(applicationContext,  R.id.imagenotileft,remoteViews, notification, notificationId)
        Glide.with(applicationContext).asBitmap().load(item.thumbURL).into(notificationTarget!!)

    }

    private fun addNotificationVideo(item:VideoItem, notificationId: Int, title:String){
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.news_notification_layout)
        remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.lion_logo_ony)
        remoteViews.setTextViewText(R.id.title, "Youtube: "+item.title.trim() +" - "+ item.author.name)


        val notificationIntent = Intent(applicationContext, WebViewActivity::class.java)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationIntent.putExtra("URL", item.link.href)
        val pendingIntent = PendingIntent.getActivity(this,  notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        val builder = NotificationCompat.Builder(this, "News_Update_Service")
                .setSmallIcon(R.drawable.lion_logo_ony)
                .setContentTitle(title)
                .setContentText(item.title)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000)
                .setCustomBigContentView(remoteViews)


        val notification = builder.build()
        val notficationMGR = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notficationMGR.notify(notificationId, notification)
        notificationTarget = NotificationTarget(applicationContext,  R.id.imagenotileft,remoteViews, notification, notificationId)
        Glide.with(applicationContext).asBitmap().load(item.group.thumbnail.url).into(notificationTarget!!)
    }
    private fun sendNewsUpdateBroadcast(){
        val lbm = LocalBroadcastManager.getInstance(this)
        lbm.sendBroadcast(Intent("NEWS_UPDATED"))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("NewsUpdateService","Service Destroyed!")
    }


}