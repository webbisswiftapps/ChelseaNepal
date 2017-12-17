package com.webbisswift.cfcnepal.ui.screens.fragments.news

import com.webbisswift.cfcnepal.base.BasePresenter
import com.webbisswift.cfcnepal.base.BaseView
import com.webbisswift.cfcnepal.domain.localdb.entities.DBNewsItem
import com.webbisswift.cfcnepal.domain.model.NewsItem
import com.webbisswift.cfcnepal.domain.model.VideoItem
import com.webbisswift.cfcnepal.ui.screens.fragments.news.adapter.NormalizedNewsItem
import java.util.*

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