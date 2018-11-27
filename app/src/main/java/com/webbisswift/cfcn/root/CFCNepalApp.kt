package com.webbisswift.cfcn.root

import android.app.Application
import android.support.multidex.MultiDexApplication
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.FirebaseDatabase
import com.kobakei.ratethisapp.RateThisApp
import com.twitter.sdk.android.core.Twitter
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.background.AppAlarmManagement
import net.danlew.android.joda.JodaTimeAndroid

/**
 * Created by apple on 12/6/17.
 */



class CFCNepalApp:MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        //ACRA.init(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        Twitter.initialize(this)
        instance = this
        JodaTimeAndroid.init(this)
        MobileAds.initialize(this, resources.getString(R.string.app_id))
        AppAlarmManagement(this).fetchNextMatchDetailsAndSetAlarm()

        RateThisApp.onCreate(this)
        // Custom condition: 3 days and 5 launches
        val config = RateThisApp.Config(3, 5)
        RateThisApp.init(config)
    }

    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(tag)
        }
    }


    companion object {
        private val TAG = CFCNepalApp::class.java.simpleName
        @get:Synchronized var instance: CFCNepalApp? = null
            private set

    }
}