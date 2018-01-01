package com.webbisswift.cfcn.domain.sharedpref

import android.content.Context
import android.preference.PreferenceManager
import org.joda.time.DateTime

/**
 * Created by apple on 12/29/17.
 */


class SettingsHelper(val c: Context){

    fun shouldShowNewsNotification():Boolean{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("notify_news", true)
    }


    fun shouldShowMatchAlert():Boolean{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("notify_mts", true)
    }

}