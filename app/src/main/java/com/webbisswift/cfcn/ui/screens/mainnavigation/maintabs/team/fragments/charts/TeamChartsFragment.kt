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
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadRVAdapter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadModel
import kotlinx.android.synthetic.main.fragment_team_charts.*

/**
 * Created by apple on 2/10/18.
 */

class TeamChartsFragment:BaseFragment(), TeamChartsContract.TeamChartsView{

    var presenter: TeamChartsContract.TeamChartsPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team_charts, null, true)
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
        chartRV.layoutManager = layoutManager

        rvAdapter = SquadRVAdapter(this.context)
        chartRV.adapter = rvAdapter
        rvAdapter.profileListener = (parentFragment as SquadRVAdapter.PlayerProfileInterface)

    }

    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadSquad()
        }

        chartTypeSelector.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = chartTypeSelector.selectedItem as String
                Log.d("ChartType", item)
                setMetricTitle(item)
                rvAdapter.orderByCharts(item)
            }
        }
    }


    override fun getPresenter(): BasePresenter  =   this.presenter as BasePresenter


    private fun setMetricTitle(metric:String){
        if (metric.contentEquals("Goals"))
            statsTitle.text = "Goals"
        else if (metric.contentEquals("Assists"))
            statsTitle.text = "Assists"
        else if (metric.contentEquals("Minutes Played"))
            statsTitle.text = "Minutes"
        else if (metric.contentEquals("Yellow Cards"))
            statsTitle.text = "Yellow Cards"
        else if (metric.contentEquals("Red Cards"))
            statsTitle.text = "Red Cards"
        else if (metric.contentEquals("Second Yellow Cards"))
            statsTitle.text = "Second Yellow"
        else if (metric.contentEquals("Appearances"))
            statsTitle.text = "Appearances"
        else if (metric.contentEquals("Starting 11"))
            statsTitle.text = "Starting"
        else if (metric.contentEquals("Bench"))
            statsTitle.text = "Bench App"
        else if (metric.contentEquals("Substituted In"))
            statsTitle.text = "Sub In"
        else if (metric.contentEquals("Substituted Out"))
            statsTitle.text = "Sub Out"
        else statsTitle.text = "#"
    }

    override fun setTeamSquad(squad: List<SMSquad.SMSquadItem>) {
        this.rvAdapter.clear()
        for(player in squad) {
            val playerItem = SquadItem(SquadAdapterConstants.TYPE_CHARTS, null, player, null, null , null , null)
            this.rvAdapter.addSquadItem(playerItem)
        }
        val item = chartTypeSelector.selectedItem as String
        this.rvAdapter.orderByCharts(item)
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