package com.webbisswift.cfcnepal.domain.sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.joda.time.DateTime

/**
 * Created by apple on 12/15/17.
 */

class NewsUpdateManager(val c:Context){

    fun saveLastNotifiedNewsTime(time: DateTime){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val editor = pref.edit()
        editor.putString("LAST_NEWS_TIME",time.toString())
        editor.apply()
    }
    fun getLastNewsUpdatedTime():DateTime{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val timeString = pref.getString("LAST_NEWS_TIME","")
        if(timeString.isNotBlank()){
            return DateTime(timeString)
        }else return DateTime.now().minusHours(15)
    }

    fun saveLastNotifiedYoutubeTime(time: DateTime){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val editor = pref.edit()
        editor.putString("LAST_YT_TIME",time.toString())
        editor.apply()
    }
    fun getLastNotifiedYoutubeTime():DateTime{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        val timeString = pref.getString("LAST_YT_TIME","")
        if(timeString.isNotBlank()){
            return DateTime(timeString)
        }else return DateTime.now().minusHours(15)
    }


}