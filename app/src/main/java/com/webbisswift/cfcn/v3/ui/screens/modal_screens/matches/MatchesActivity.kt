package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.adapters.MatchesRVAdapter
import kotlinx.android.synthetic.main.v3_matches_activity_modal.*
import android.support.v7.widget.DividerItemDecoration
import com.webbisswift.cfcn.base.BaseActivity
import com.webbisswift.cfcn.domain.model.v2.SMMatch


/**
 * Created by apple on 12/7/17.
 */


class MatchesActivity: BaseActivity(), MatchesContract.MatchesView {


    var presenter: MatchesPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v3_matches_activity_modal)
        initView()
        setupRecyclerView()
    }

    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val model = MatchesModel(FirebaseDatabase.getInstance())
        this.presenter = MatchesPresenter(model)

        backButton.setOnClickListener { finish() }
    }


    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var matchesAdapter:MatchesRVAdapter

    fun setupRecyclerView(){


            layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            matchesRecyclerV.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(matchesRecyclerV.context, LinearLayoutManager.VERTICAL)
            matchesRecyclerV.addItemDecoration(dividerItemDecoration)




        matchesAdapter = MatchesRVAdapter(this)
        matchesRecyclerV.adapter = matchesAdapter
    }

    /**
     * Matches View Implementation
     */



    override fun showLoading() {

        if(matchesSwitcher?.currentView?.id != R.id.matchesProgress){
            matchesSwitcher?.showPrevious()
        }
    }


    override fun addMatches(fixtures: List<SMMatch>, scrollTo:Int) {
        this.matchesAdapter.clear()
        this.matchesAdapter.addMatches(fixtures)
        matchesRecyclerV?.scrollToPosition(scrollTo)
    }



    override fun hideLoading() {
        //can hide now
        if(matchesSwitcher?.currentView?.id == R.id.matchesProgress){
            matchesSwitcher?.showNext()
        }
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

}