package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined

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
import com.webbisswift.cfcn.domain.model.v2.SMTransferItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadRVAdapter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in.TransferInContract
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in.TransferInModel
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in.TransferInPresenter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by biswas on 22/12/2017.
 */

class TransferInFragment: BaseFragment(), TransferInContract.TransferInView{

    var presenter: TransferInPresenter? = null


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
        val model = TransferInModel(FirebaseDatabase.getInstance())
        this.presenter = TransferInPresenter(model)
    }



    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadTransferInPlayers()
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
        rvAdapter.profileListener = (parentFragment as SquadRVAdapter.PlayerProfileInterface)

    }

    override fun showLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = true
        }, 600)
    }



    override fun addPlayers(players: List<SMTransferItem>) {

        if(players.size > 1) {
            for ((index, value) in players.withIndex()) {
                val playerItem = SquadItem(SquadAdapterConstants.TYPE_TRANSFER_IN, null, null, null, null, value, null)
                this.rvAdapter.addSquadItem(playerItem)
                if(index % 8 == 0){
                    val adItem = SquadItem(SquadAdapterConstants.TYPE_AD, null, null, null, null, null, null)
                    this.rvAdapter.addSquadItem(adItem)
                }
            }
        }else{
            val playerItem = SquadItem(SquadAdapterConstants.TYPE_TRANSFER_IN, null, null, null, null, players[0], null)
            this.rvAdapter.addSquadItem(playerItem)
            val adItem = SquadItem(SquadAdapterConstants.TYPE_AD, null, null, null, null, null, null)
            this.rvAdapter.addSquadItem(adItem)
        }

        this.rvAdapter.notifyDataSetChanged()
    }

    override fun clearList() {
        this.rvAdapter.clear()
    }

    override fun hideLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = false
        }, 600)

        if(rvVS?.currentView?.id == R.id.errorRV)
            rvVS?.showPrevious()
    }

    override fun showNoPlayers() {
        if(rvVS?.currentView?.id != R.id.errorRV)
            rvVS?.showNext()

        errorRV.text = "No transfer in data."
    }


    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}