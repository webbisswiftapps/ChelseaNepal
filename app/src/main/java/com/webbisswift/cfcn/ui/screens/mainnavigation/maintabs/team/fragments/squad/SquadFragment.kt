package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadRVAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by biswas on 21/12/2017.
 */

class SquadFragment:BaseFragment(), SquadContract.SquadView{

    var presenter: SquadPresenter? = null


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
        val model = SquadModel(FirebaseDatabase.getInstance())
        this.presenter = SquadPresenter(model)
    }



    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadCurrentSquad()
        }
    }

    private lateinit var rvAdapter: SquadRVAdapter
    private fun setupRecyclerView(){


            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            rv.layoutManager = layoutManager

        rvAdapter = SquadRVAdapter(this.context)
        rv.adapter = rvAdapter
        rvAdapter.profileListener = (parentFragment as SquadRVAdapter.PlayerProfileInterface)

    }

    override fun showSquadLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = true
        }, 600)
    }

    override fun addCoach(coach: String) {
        val titleItem = SquadItem(SquadAdapterConstants.TYPE_TITLE, "Manager", null, null, null , null, null)
        val coachItem = SquadItem(SquadAdapterConstants.TYPE_COACH, null, null, coach, null, null, null)
        val adItem = SquadItem(SquadAdapterConstants.TYPE_AD, null , null, null, null, null, null)
        this.rvAdapter.addSquadItem(titleItem)
        this.rvAdapter.addSquadItem(coachItem)
        this.rvAdapter.addSquadItem(adItem)
        this.rvAdapter.notifyDataSetChanged()
    }

    override fun addSquadGroup(title: String, players: List<SMSquad.SMSquadItem>) {
        val titleItem = SquadItem(SquadAdapterConstants.TYPE_TITLE, title, null, null, null , null , null)
        this.rvAdapter.addSquadItem(titleItem)

        for(player in players) {
            val playerItem = SquadItem(SquadAdapterConstants.TYPE_SQUAD_PLAYER, null, player, null, null , null , null)
            this.rvAdapter.addSquadItem(playerItem)
        }

        this.rvAdapter.notifyDataSetChanged()
    }

    override fun clearList() {
        this.rvAdapter.clear()
    }

    override fun hideSquadLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = false
        }, 600)

        if(rvVS?.currentView?.id == R.id.errorRV)
            rvVS?.showPrevious()
    }

    override fun showNoSquadError() {
        if(rvVS?.currentView?.id != R.id.errorRV)
            rvVS?.showNext()

        errorRV.text = "Could not load squad info."
    }


    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}