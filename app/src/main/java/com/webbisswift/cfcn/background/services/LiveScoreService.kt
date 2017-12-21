package com.webbisswift.cfcn.background.services

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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.LSMatch
import com.webbisswift.cfcn.domain.model.LiveScoreResponse
import com.webbisswift.cfcn.domain.model.MatchEvent
import com.webbisswift.cfcn.domain.sharedpref.LiveUpdateScoreManager
import com.webbisswift.cfcn.root.CFCNepalApp
import com.webbisswift.cfcn.ui.screens.home.MainActivity
import org.json.JSONObject

/**
 * Created by apple on 12/9/17.
 */

class LiveScoreService() : Service() {

    internal var h = Handler()
    internal var queue: RequestQueue = CFCNepalApp.instance?.requestQueue!!

    private var lbm:LocalBroadcastManager? = null
    private var luSM:LiveUpdateScoreManager? = null


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

        if(luSM == null)
            luSM = LiveUpdateScoreManager(this)
        
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
        val matchStarted = luSM?.getMatchStarted()!!
        val halfTime = luSM?.getMatchHalfTime()!!
        val lastEventId = luSM?.getLastEventId()!!
        val secHalf = luSM?.getMatchSecondHalfStarted()!!

        Log.d("LiveScoreService","Cache: STARTED "+matchStarted)
        Log.d("LiveScoreService","Cache: HT "+halfTime)
        Log.d("LiveScoreService", "Cache: 2nd Half: "+secHalf)
        Log.d("LiveScoreService","Cache: LAST EVENT "+lastEventId)


        if(matches != null && matches.isNotEmpty()){

            val currentMatch = matches[0]

            if(currentMatch.started){
                Log.d("LiveScoreService", "Match Started | Status: "+currentMatch.status+" Events: "+currentMatch.events)

                if(!matchStarted){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "Match begins."
                    Log.d("LiveScoreService","Match begins.. Notified.")
                    notifyEvent(eventDesc, title)
                }

                var halfTimeL = halfTime

                if(!halfTime && currentMatch.status.contentEquals("HT")){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "1st half ended."
                    notifyEvent(eventDesc, title)
                    Log.d("LiveScoreService","1st half ends .. notified.")
                    halfTimeL = true
                }

                var secHalfL = secHalf
                if(halfTime && !secHalf && !currentMatch.status.contentEquals("HT")){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "2nd half begins."
                    notifyEvent(eventDesc, title)
                    Log.d("LiveScoreService","2nd half begins..notified")
                    secHalfL = true
                }



                var lastEventL = ""
                if(lbm != null){

                    val scoreIntent = Intent("LIVE_SCORE_EVENT")
                    scoreIntent.putExtra("STATUS", currentMatch.status)
                    scoreIntent.putExtra("HOME_SCORE", currentMatch.score[0])
                    scoreIntent.putExtra("AWAY_SCORE", currentMatch.score[1])

                    val events = ArrayList<MatchEvent>()
                    events.addAll(currentMatch.events)
                    scoreIntent.putParcelableArrayListExtra("EVENTS", events)

                    val eventSize = events.size
                    if(eventSize > 0 && !lastEventId.contentEquals(events[eventSize - 1].id)){

                        val event = events[eventSize-1]

                        val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                        val eventDesc = event.minute+" "+event.type.toUpperCase()+": "+event.home+event.away
                        notifyEvent(eventDesc, title)
                        Log.d("LiveScoreService","Match event notified "+event)

                    }

                    if(eventSize > 0){
                        lastEventL = events[eventSize - 1].id
                    }
                    lbm?.sendBroadcast(scoreIntent) /* Send score update broadcast */
                }

                if(currentMatch.status.contentEquals("FT")){
                    val title = currentMatch.home + " "+currentMatch.score[0]+" - "+currentMatch.score[1]+" "+currentMatch.away
                    val eventDesc = "Full Time"
                    Log.d("LiveScoreService","Match full time notified")

                    notifyEvent(eventDesc, title)
                    luSM?.reset()
                    stopSelf()
                }else{
                    Log.d("LiveScoreService","New: STARTED "+true)
                    Log.d("LiveScoreService","New: HT "+halfTimeL)
                    Log.d("LiveScoreService","New: LAST EVENT "+lastEventL)
                    luSM?.setLiveMatchLastEvent(true, halfTimeL, secHalfL, lastEventL, currentMatch.score[0].toString(), currentMatch.score[1].toString(), currentMatch.status)
                    subscribeToLive()
                }

            }else{
                Log.d("LiveScoreService","Match not started yet")
                luSM?.reset()
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
                .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.whistle))
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