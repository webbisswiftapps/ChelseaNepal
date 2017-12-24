package com.webbisswift.cfcn.ui.screens.team.fragments.sidelined

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.InjuredPlayer
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadItem
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadRVAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by biswas on 22/12/2017.
 */

class SidelinedFragment: BaseFragment(), SidelinedContract.SidelinedView{

    var presenter: SidelinedPresenter? = null


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


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val model = TeamInfoModel(FirebaseDatabase.getInstance())
        this.presenter = SidelinedPresenter(model)
    }



    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadSidelinedPlayers()
        }
    }

    private lateinit var rvAdapter: SquadRVAdapter
    private fun setupRecyclerView(){


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        rv.addItemDecoration(dividerItemDecoration)

        rvAdapter = SquadRVAdapter(this.context)
        rv.adapter = rvAdapter

    }

    override fun showSidelinedLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = true
        }, 600)
    }



    override fun addPlayers(players: List<InjuredPlayer>) {

        if(players.size > 1) {
            for ((index, value) in players.withIndex()) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_INJURED_PLAYER, null, null, null, value, null, null)
                this.rvAdapter.addSquadItem(playerItem)
                if(index % 5 == 0){
                    val adItem = SquadItem(SquadAdapterConstants.TYPE_AD, null, null, null, null, null, null)
                    this.rvAdapter.addSquadItem(adItem)
                }
            }
        }else{
            val playerItem = SquadItem(SquadAdapterConstants.TYPE_INJURED_PLAYER, null, null, null, players[0], null, null)
            this.rvAdapter.addSquadItem(playerItem)
            val adItem = SquadItem(SquadAdapterConstants.TYPE_AD, null, null, null, null, null, null)
            this.rvAdapter.addSquadItem(adItem)
        }

        this.rvAdapter.notifyDataSetChanged()
    }

    override fun clearList() {
        this.rvAdapter.clear()
    }

    override fun hideSidelinedLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = false
        }, 600)

        if(rvVS?.currentView?.id == R.id.errorRV)
            rvVS?.showPrevious()
    }

    override fun showNoSidelined() {
        if(rvVS?.currentView?.id != R.id.errorRV)
            rvVS?.showNext()

        errorRV.text = "No players sidelined currently."
    }


    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}