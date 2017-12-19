package com.webbisswift.cfcn.ui.screens.home.fragments.matches

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.screens.home.fragments.matches.adapters.MatchesRVAdapter
import kotlinx.android.synthetic.main.fragment_matches.*
import android.support.v7.widget.DividerItemDecoration



/**
 * Created by apple on 12/7/17.
 */


class MatchesFragment: BaseFragment(), MatchesContract.MatchesView {


    var presenter: MatchesPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matches, null, true)
        initView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val model = MatchesModel(FirebaseDatabase.getInstance())
        this.presenter = MatchesPresenter(model)
    }


    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var matchesAdapter:MatchesRVAdapter

    fun setupRecyclerView(){

        val isWide = context?.resources?.getBoolean(R.bool.isWide)!!
        if(isWide) {
            layoutManager = GridLayoutManager(context, 2)
            matchesRecyclerV.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(matchesRecyclerV.context, GridLayoutManager.HORIZONTAL)
            matchesRecyclerV.addItemDecoration(dividerItemDecoration)

        }else{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            matchesRecyclerV.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(matchesRecyclerV.context, LinearLayoutManager.VERTICAL)
            matchesRecyclerV.addItemDecoration(dividerItemDecoration)
        }



        matchesAdapter = MatchesRVAdapter(this.context)
        matchesRecyclerV.adapter = matchesAdapter
    }

    /**
     * Matches View Implementation
     */


    var loadingCount = 0

    override fun showLoading() {
        this.loadingCount ++

        if(matchesSwitcher?.currentView?.id != R.id.matchesProgress){
            matchesSwitcher?.showPrevious()
        }
    }


    override fun addFixtures(fixtures: List<Match>) {
        this.matchesAdapter.addMatches(fixtures)
    }

    override fun addResults(results: List<Match>) {
        this.matchesAdapter.addMatches(results)
        this.matchesRecyclerV?.scrollToPosition(results.size - 1)
    }

    override fun hideLoading() {
        this.loadingCount --
        if(loadingCount <= 0){
            //can hide now
            if(matchesSwitcher?.currentView?.id == R.id.matchesProgress){
                matchesSwitcher?.showNext()
            }
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