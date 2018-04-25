package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem

interface NewsStreamContract{

    interface NewsStreamView : BaseView {

        fun showLoading()
        fun hideLoading()
        fun clearCurrentList()
        fun addNewsSection(items:List<NewsStreamItem>)
    }


    interface NewsStreamPresenter: BasePresenter {
        fun loadNews()
        //fun updateNews()
    }


    interface NewsStreamModel{
        fun subscribe(listener:ValueEventListener)
        fun unsubscribe()
    }

}