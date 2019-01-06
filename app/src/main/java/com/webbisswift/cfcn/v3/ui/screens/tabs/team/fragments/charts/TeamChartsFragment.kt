package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMSquad

import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadModel
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters.SquadItem
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters.SquadRVAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by apple on 2/10/18.
 */

class TeamChartsFragment:BaseFragment(), TeamChartsContract.TeamChartsView{

    var presenter: TeamChartsContract.TeamChartsPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rv, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

     override fun initView() {
        val model = SquadModel(FirebaseDatabase.getInstance())
        this.presenter = TeamChartsPresenter(model)
    }

    private lateinit var rvAdapter: SquadRVAdapter

    private fun setupRecyclerView(){

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.layoutManager = layoutManager

        rvAdapter = SquadRVAdapter(this.context)
        rv.adapter = rvAdapter
        rvAdapter.profileListener = (parentFragment as SquadRVAdapter.PlayerProfileInterface)

    }

    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadSquad()
        }

    }


    override fun getPresenter(): BasePresenter  =   this.presenter as BasePresenter




    override fun setTeamSquad(squad: List<SMSquad.SMSquadItem>) {
        this.rvAdapter.clear()

        //goals section
        val goalScorers = squad.filter { it.goals > 0 }
        if(goalScorers.isNotEmpty()) {
            val gScorers = goalScorers.sortedByDescending { it.goals }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Goals", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Goals")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }


        //assists section
        val assistScorers = squad.filter { it.assists > 0 }
        if(assistScorers.isNotEmpty()) {
            val gScorers = assistScorers.sortedByDescending { it.assists }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Assists", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Assists")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }


        val ycScorers = squad.filter { it.yellowcards > 0 }
        if(ycScorers.isNotEmpty()) {
            val gScorers = ycScorers.sortedByDescending { it.yellowcards }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Yellow Cards", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Yellow Cards")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }


        val rcScorers = squad.filter { it.redcards > 0 }
        if(rcScorers.isNotEmpty()) {
            val gScorers = rcScorers.sortedByDescending { it.redcards }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Red Cards", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Red Cards")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }


        //minutes section
        val minuteScorers = squad.filter { it.minutes > 0 }
        if(minuteScorers.isNotEmpty()) {
            val gScorers = minuteScorers.sortedByDescending { it.minutes }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Minutes Played", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Minutes Played")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }


        val appScorers = squad.filter { it.appearences > 0 }
        if(appScorers.isNotEmpty()) {
            val gScorers = appScorers.sortedByDescending { it.appearences }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Appearances", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Appearances")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }



        val startingScorers = squad.filter { it.lineups > 0 }
        if(startingScorers.isNotEmpty()) {
            val gScorers = startingScorers.sortedByDescending { it.lineups }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_TITLE, "Starting 11", null, null, null, null, null, null))
            for (player in gScorers.take(5)) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null, null, null, "Starting 11")
                this.rvAdapter.addSquadItem(playerItem)
            }
            this.rvAdapter.addSquadItem(SquadItem(SquadAdapterConstants.TYPE_FOOTER, null, null, null, null, null, null, null))
        }







    }

    override fun setTeamSquadLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = true
        }, 600)
    }

    override fun hideTeamSquadLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = false
        }, 600)

        if(rvVS?.currentView?.id == R.id.errorRV)
            rvVS?.showPrevious()
    }

    override fun showSquadNotAvailable() {
        if(rvVS?.currentView?.id != R.id.errorRV)
            rvVS?.showNext()

        errorRV?.text = "Oops! Data not available."
    }

    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}