package com.webbisswift.cfcnepal.background.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.RemoteViews
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.webbisswift.cfcnepal.R
import com.webbisswift.cfcnepal.domain.model.LSMatch
import com.webbisswift.cfcnepal.domain.model.LiveScoreResponse
import com.webbisswift.cfcnepal.domain.model.MatchEvent
import com.webbisswift.cfcnepal.root.CFCNepalApp
import com.webbisswift.cfcnepal.ui.screens.main.MainActivity
import com.webbisswift.cfcnepal.ui.screens.webview.WebViewActivity
import org.json.JSONObject

/**
 * Created by apple on 12/9/17.
 */

class LiveScoreService() : Service() {

    internal var h = Handler()
    internal var queue: RequestQueue = CFCNepalApp.instance?.requestQueue!!

    private var lbm:LocalBroadcastManager? = null

    private var lastEvent:MatchEvent? = null
    private var matchStarted:Boolean = false
    private var halfTime:Boolean = false




    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStart(intent: Intent?, startId: Int) {
        loadLiveScore()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        loadLiveScore()
        return Service.START_STICKY
    }






    private fun loadLiveScore() {
         Log.d("LiveScoreService", "... service started..")
        if(lbm == null)
            lbm = LocalBroadcastManager.getInstance(this)
        
        val newsReceiver = object: Response.Listener<JSONObject> {
            override fun onResponse(response: JSONObject?) {
                val livescoreResponse = LiveScoreResponse.parseResponse(response.toString())
                parseAndUpdateScores(livescoreResponse)
            }

        }

        val errorReceiver = object: Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("LiveScoreService", error.toString())
                subscribeToLive()
            }
        }


        val request = JsonObjectRequest(Request.Method.GET, "http://api.statsfc.com/crowdscores/live.php?key=VsIkrxOyDBTSi1KkdIl7gdFb5dqjvOihsJ1PxVnN&domain=statsfc.com&team=chelsea&timezone=Asia/Kathmandu&goals=true",
                null, newsReceiver, errorReceiver)
        this.queue.add(request)

    }


    private fun subscribeToLive(){
        h.postDelayed({ loadLiveScore() }, 30000) /* minute by minute update */
    }



    private fun parseAndUpdateScores(response:LiveScoreResponse)
    {
        val matches:List<LSMatch>? = response.matches
        if(matches != null && matches.isNotEmpty()){

            val currentMatch = matches[0]

            if(currentMatch.started){
                Log.d("LiveScoreService", "Match Started | Status: "+currentMatch.status+" Events: "+currentMatch.events)

                if(!matchStarted){
                    matchStarted = true

                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "Match begins."
                    Log.d("LiveScoreService","Match begins.. Notified.")
                    notifyEvent(eventDesc, title)
                }


                if(!halfTime && currentMatch.status.contentEquals("HT")){
                    halfTime = true
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "1st half ended."
                    notifyEvent(eventDesc, title)
                    Log.d("LiveScoreService","1st half ends .. notified.")
                }

                if(halfTime && !currentMatch.status.contentEquals("HT")){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "2nd half begins."
                    notifyEvent(eventDesc, title)
                    Log.d("LiveScoreService","2nd half begins..notified")
                }



                if(lbm != null){

                    val scoreIntent = Intent("LIVE_SCORE_EVENT")
                    scoreIntent.putExtra("STATUS", currentMatch.status)
                    scoreIntent.putExtra("HOME_SCORE", currentMatch.score[0])
                    scoreIntent.putExtra("AWAY_SCORE", currentMatch.score[1])

                    val events = ArrayList<MatchEvent>()
                    events.addAll(currentMatch.events)
                    scoreIntent.putParcelableArrayListExtra("EVENTS", events)

                    val eventSize = events.size
                    if(eventSize > 0 && lastEvent != events[eventSize - 1]){

                        val event = events[eventSize-1]

                        val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                        val eventDesc = event.minute+" "+event.type.toUpperCase()+": "+event.home+event.away
                        notifyEvent(eventDesc, title)
                        Log.d("LiveScoreService","Match event notified "+event)
                        this.lastEvent = events[eventSize -1]
                    }

                    lbm?.sendBroadcast(scoreIntent) /* Send score update broadcast */
                }

                if(currentMatch.status.contentEquals("FT")){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "Full Time"
                    Log.d("LiveScoreService","Match full time notified")

                    notifyEvent(eventDesc, title)
                    stopSelf()
                }else{
                    subscribeToLive()
                }

            }else{
                Log.d("LiveScoreService","Match not started yet")
                subscribeToLive()
            }
        }else stopSelf()
    }


    private fun notifyEvent(eventDesc:String, title:String){

        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,  404, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        val builder = NotificationCompat.Builder(this, "Livescore_Service")
                .setSmallIcon(R.drawable.lion_logo_ony)
                .setContentTitle(title)
                .setContentText(eventDesc)
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.news_updated))
                .setLights(Color.BLUE, 3000, 3000)


        val notification = builder.build()
        val notficationMGR = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notficationMGR.notify(404, notification)
    }


    override fun onDestroy() {
        Log.d("LiveScoreService", "Service Destroyed.")
        super.onDestroy()
    }


}