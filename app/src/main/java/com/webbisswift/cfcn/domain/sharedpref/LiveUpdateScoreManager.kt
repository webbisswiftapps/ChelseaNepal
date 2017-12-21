package com.webbisswift.cfcn.domain.sharedpref

import android.content.Context
import android.preference.PreferenceManager
import com.webbisswift.cfcn.domain.model.MatchEvent
import org.joda.time.DateTime

/**
 * Created by apple on 12/20/17.
 */


class LiveUpdateScoreManager(val c: Context){

    fun setLiveMatchLastEvent(started:Boolean, halfTime: Boolean, secondHalf:Boolean,lastEvent:String, lastScoreHome:String, lastScoreAway:String, status:String){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val editor = pref.edit()
        editor.putBoolean("LIVE_MATCH_STARTED",started)
        editor.putBoolean("LIVE_MATCH_HALF_TIME", halfTime)
        editor.putBoolean("LIVE_MATCH_SECOND_HALF", secondHalf)
        editor.putString("LIVE_MATCH_LAST_EVENT", lastEvent)
        editor.putString("LIVE_MATCH_LAST_SCORE_HOME", lastScoreHome)
        editor.putString("LIVE_MATCH_LAST_SCORE_AWAY", lastScoreAway)
        editor.putString("LIVE_MATCH_STATUS", status)
        editor.apply()

    }
    fun getMatchStarted(): Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("LIVE_MATCH_STARTED",false)
    }

    fun getMatchHalfTime(): Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("LIVE_MATCH_HALF_TIME",false)
    }

    fun getMatchSecondHalfStarted(): Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("LIVE_MATCH_SECOND_HALF",false)
    }

    fun getLastEventId(): String {
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getString("LIVE_MATCH_LAST_EVENT", "")
    }

    fun getLastHomeScore():String{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getString("LIVE_MATCH_LAST_SCORE_HOME", "0")
    }

    fun getLastAwayScore():String{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getString("LIVE_MATCH_LAST_SCORE_AWAY", "0")
    }

    fun getLastStatus():String{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getString("LIVE_MATCH_STATUS", "")
    }

    fun reset(){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val editor = pref.edit()
        editor.putBoolean("LIVE_MATCH_STARTED",false)
        editor.putBoolean("LIVE_MATCH_HALF_TIME", false)
        editor.putString("LIVE_MATCH_LAST_EVENT", "")
        editor.apply()
    }


}