package com.webbisswift.cfcn.ui.screens.home.fragments.news

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.localdb.AppDatabase
import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem
import com.webbisswift.cfcn.ui.custom_views.ItemOffsetDecoration
import com.webbisswift.cfcn.ui.screens.home.fragments.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*


/**
 * Created by apple on 12/8/17.
 */


class NewsFragment: BaseFragment(), NewsContract.NewsView {


    var presenter: NewsPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, null, true)
        if(this.presenter == null) {
            initView()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        newsRefresh.setOnRefreshListener {
            this.presenter?.updateNews()
        }
        super.onViewCreated(view, savedInstanceState)
    }




    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val newsDAO = AppDatabase.getInstance(context!!).newsDao()
        val lbm = LocalBroadcastManager.getInstance(context!!)
        val model = NewsModel(newsDAO,context!!,lbm)
        this.presenter = NewsPresenter(model)
    }


    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private lateinit var newsAdapter: NewsAdapter

    fun setupRecyclerView(){

        val isWide = context?.resources?.getBoolean(R.bool.isWide)!!
        if(isWide) {
            val layoutManager = GridLayoutManager(context, 3)
            newsRecyclerView.layoutManager = layoutManager

            val dividerItemDecoration = ItemOffsetDecoration(15)
            newsRecyclerView.addItemDecoration(dividerItemDecoration)

        }else{
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            newsRecyclerView.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(newsRecyclerView.context, LinearLayoutManager.VERTICAL)
            newsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        newsAdapter = NewsAdapter(this.context)
        newsRecyclerView.adapter = newsAdapter
    }

    /**
     * News View Implementation
     */



    override fun showLoading() {

        newsRefresh?.postDelayed({
            newsRefresh?.isRefreshing = true
        }, 600)
    }


    override fun addNewsSection(items: List<DBNewsItem>) {
        newsAdapter?.addNewsSection(items)
    }

    override fun hideLoading() {
        newsRefresh?.postDelayed({
            newsRefresh?.isRefreshing = false
        }, 600)
    }

    override fun clearCurrentList() {
        this.newsAdapter?.clear()
    }

    /**
     * Alerts & Error Methods
     */
    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        val nm = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(100)
    }
}