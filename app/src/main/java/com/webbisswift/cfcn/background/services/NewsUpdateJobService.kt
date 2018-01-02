package com.webbisswift.cfcn.background.services

import android.app.job.JobService
import android.app.job.JobParameters
import android.content.Intent
import android.util.Log


/**
 * Created by biswas on 02/01/2018.
 */

class NewsUpdateJobService:JobService(){



    private val TAG = "NewsUpdateJobService"

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "NewsUpdate Job Started.. starting service now..")
        val service = Intent(getApplicationContext(), NewsUpdateService::class.java)
        service.putExtra("SHOULD_NOTIFY", true)
        getApplicationContext().startService(service)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        return true
    }
}