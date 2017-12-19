package com.webbisswift.cfcn.ui.screens.home.fragments.news

import android.util.Log
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem


/**
 * Created by apple on 12/8/17.
 */


class NewsPresenter(val model: NewsModel): NewsContract.NewsPresenter, NewsContract.NewsResponseListener {

    var view: NewsContract.NewsView? = null

    init {
        model.listener = this
    }

    /**
     * Non Lifecycle Methods : Load Info methods
     * **/

    override fun loadNews() {
        view?.showLoading()
        model.getAllNews(true, true)
    }


    override fun updateNews() {
        view?.showLoading()
        model.getAllNews(true, false)
    }

    /**
     * News Response Listener
     */


    override fun onError(error: String) {
       view?.hideLoading()
    }

    override fun newsSetLoaded(news: List<DBNewsItem>) {
        view?.clearCurrentList()
        view?.hideLoading()
        view?.addNewsSection(news)
    }

    /**
     * LifeCycle Methods
     */

    override fun resume() {
        Log.d("NewsPresenter","News Fragment is resumed!")
        model.subscribe()
    }

    override fun pause() {
        Log.d("NewsPresenter","News Fragment is paused!")
        model.unsubscribe()
    }

    override fun detachView() {
        Log.d("NewsPresenter","News Fragment is detached!")

    }

    override fun destroy() {
        Log.d("NewsPresenter","News Fragment is destroyed!")
    }

    override fun attachView(view: BaseView) {
        Log.d("NewsPresenter","News Fragment attached")
        this.view = view as NewsContract.NewsView
        loadNews()
    }

}
