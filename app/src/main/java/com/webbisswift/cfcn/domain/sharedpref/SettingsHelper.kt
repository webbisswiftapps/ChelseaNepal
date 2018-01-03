package com.webbisswift.cfcn.domain.sharedpref

import android.content.Context
import android.content.SharedPreferences
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


    fun getUserCurrentCountry():String{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getString("user_country", "ERR_NO_COUNTRY_SET")
    }

    fun setUserCurrentCountry(country:String){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        prefEditor.putString("user_country", country)
        prefEditor.commit()
    }


    fun setDontAskCountryAgain(){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        prefEditor.putBoolean("user_country_dontask", true)
        prefEditor.apply()
    }

    fun getDontAskCountryAgain():Boolean{
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        return pref.getBoolean("user_country_dontask", false)
    }


    fun registerCountryChangeListener(listener:SharedPreferences.OnSharedPreferenceChangeListener){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterCountryChangeListener(listener:SharedPreferences.OnSharedPreferenceChangeListener){
        val pref = PreferenceManager.getDefaultSharedPreferences(c)
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

}