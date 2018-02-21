package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem

/**
 * Created by apple on 12/8/17.
 */

interface NewsContract{

    interface NewsView : BaseView {

        fun showLoading()
        fun hideLoading()
        fun clearCurrentList()
        fun addNewsSection(items:List<DBNewsItem>)
    }


    interface NewsPresenter:BasePresenter{
       fun loadNews()
        fun updateNews()
    }


    interface NewsModel{

        fun getAllNews(isRefresh:Boolean, fillWithOld:Boolean)
        fun subscribe()
        fun unsubscribe()
    }

    interface NewsResponseListener{
        fun newsSetLoaded(news:List<DBNewsItem>)
        fun onError(error:String)
    }



}