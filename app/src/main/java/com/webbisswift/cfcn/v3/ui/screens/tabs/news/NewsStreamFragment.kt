package com.webbisswift.cfcn.v3.ui.screens.tabs.news

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem
import com.webbisswift.cfcn.v3.custom_views.ItemOffsetDecoration
import com.webbisswift.cfcn.v3.ui.screens.tabs.news.adapter.AdType
import com.webbisswift.cfcn.v3.ui.screens.tabs.news.adapter.NewsAdapter
import com.webbisswift.cfcn.v3.ui.screens.tabs.news.adapter.NormalizedNewsItem
import kotlinx.android.synthetic.main.v3_layout_news.*

class NewsStreamFragment: BaseFragment(), NewsStreamContract.NewsStreamView {


    var presenter: NewsStreamPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_layout_news, null, true)
        if(this.presenter == null) {
            initView()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }




    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val firebaseDB = FirebaseDatabase.getInstance()
        val model = NewsStreamModel(firebaseDB)
        this.presenter = NewsStreamPresenter(model)

        newsRefresh?.isEnabled = false
    }


    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private lateinit var newsAdapter: NewsAdapter

    fun setupRecyclerView(){

        newsAdapter = NewsAdapter(this.context)
        val isWide = context?.resources?.getBoolean(R.bool.isWide)!!
        if(isWide) {
            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            newsRecyclerView.layoutManager = layoutManager

        }else{
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            newsRecyclerView.layoutManager = layoutManager

        }

        newsRecyclerView.adapter = newsAdapter
    }

    /**
     * News View Implementation
     */



    override fun showLoading() {

        newsRefresh?.postDelayed({
            newsRefresh?.isEnabled = true
            newsRefresh?.isRefreshing = true
        }, 600)
    }


    override fun addNewsSection(items: List<NewsStreamItem>) {
        var normalizedList = ArrayList<NormalizedNewsItem>()
        for(item in items){
            //if(isWide)item.isHeading = true
            normalizedList.add(NormalizedNewsItem(item, false, AdType.SMALL))
        }
        newsAdapter.addNewsSection(normalizedList)
    }

    override fun hideLoading() {
        newsRefresh?.postDelayed({
            newsRefresh?.isRefreshing = false
            newsRefresh?.isEnabled = false
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