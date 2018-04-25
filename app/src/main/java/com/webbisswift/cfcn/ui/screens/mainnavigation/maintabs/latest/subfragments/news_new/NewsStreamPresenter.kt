package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.FormItem
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem

class NewsStreamPresenter(val model: NewsStreamModel): NewsStreamContract.NewsStreamPresenter {

    var view: NewsStreamContract.NewsStreamView? = null


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/

    override fun loadNews() {
        view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards NewsStreamItem>>() {}
                    val formItems: List<NewsStreamItem>? = dataSnapshot.getValue(t)
                    presentNews(formItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideLoading()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribe(nextMatchListener)
    }


    fun presentNews(news:List<NewsStreamItem>?){
        view?.hideLoading()
        if(news != null && news.isNotEmpty()){
            this.view?.clearCurrentList()
            this.view?.addNewsSection(news)
        }
    }


    /**
     * LifeCycle Methods
     */

    override fun resume() {
        Log.d("NewsStreamPresenter", "News Fragment is resumed!")
    }

    override fun pause() {
        Log.d("NewsStreamPresenter", "News Fragment is paused!")

    }

    override fun detachView() {
        Log.d("NewsStreamPresenter", "News Fragment is detached!")
        this.view = null
        model.unsubscribe()
    }

    override fun destroy() {
        Log.d("NewsStreamPresenter", "News Fragment is destroyed!")
    }

    override fun attachView(view: BaseView) {
        Log.d("NewsStreamPresenter", "News Fragment attached")
        this.view = view as NewsStreamContract.NewsStreamView
        loadNews()
    }

}

