package com.webbisswift.cfcn.ui.screens.home.fragments.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.webbisswift.cfcn.domain.localdb.dao.NewsDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.app.NotificationManager
import com.webbisswift.cfcn.background.AppAlarmManagement


/**
 * Created by apple on 12/8/17.
 */

class NewsModel(private val newsDAO: NewsDao, private val context:Context, private val lbm:LocalBroadcastManager):NewsContract.NewsModel, BroadcastReceiver(){

    var listener:NewsContract.NewsResponseListener? = null



    override fun getAllNews(isRefresh: Boolean, fillWithOld:Boolean) {
        if(fillWithOld) loadFromDatabase()
        if(isRefresh) startNewsUpdateService()
    }


    private fun loadFromDatabase(){
        doAsync {
            val newsList = newsDAO.getAllNews()
            uiThread{
                if(newsList.isNotEmpty() ){
                    if(listener != null){
                        listener?.newsSetLoaded(newsList)
                    }
                }
            }
        }
    }

    private fun startNewsUpdateService(){
        AppAlarmManagement(context).startNewsUpdateService(false)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(listener != null){
            loadFromDatabase()
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(808)
        }
    }

    override fun subscribe() {
        this.lbm.registerReceiver(this, IntentFilter("NEWS_UPDATED"))
    }

    override fun unsubscribe() {
        this.lbm.unregisterReceiver(this)
    }





}