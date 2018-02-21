package com.webbisswift.cfcn.root

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.background.AppAlarmManagement
import net.danlew.android.joda.JodaTimeAndroid
import org.acra.ACRA
import org.acra.ReportField
import org.acra.ReportingInteractionMode
import org.acra.annotation.ReportsCrashes

/**
 * Created by apple on 12/6/17.
 */


@ReportsCrashes(formKey = "",
        mode = ReportingInteractionMode.TOAST,
        customReportContent = arrayOf(ReportField.DEVICE_ID, ReportField.APP_VERSION_NAME,
            ReportField.ANDROID_VERSION, ReportField.BRAND,ReportField.PHONE_MODEL,
            ReportField.CUSTOM_DATA, ReportField.STACK_TRACE ),
        resToastText = R.string.crash_text,
        forceCloseDialogAfterToast = false, // optional, default false
        mailTo = "webbisswiftapps@gmail.com"
)
class CFCNepalApp:Application(){

    override fun onCreate() {
        super.onCreate()
        //ACRA.init(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        instance = this
        JodaTimeAndroid.init(this)
        MobileAds.initialize(this, resources.getString(R.string.app_id))
        AppAlarmManagement(this).fetchNextMatchDetailsAndSetAlarm()
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