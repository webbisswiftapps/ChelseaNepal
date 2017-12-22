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
import com.webbisswift.cfcn.domain.model.TransferOutPlayer
import com.webbisswift.cfcn.domain.model.TrasnferInPlayer
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadAdapterConstants
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadItem
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadRVAdapter
import com.webbisswift.cfcn.ui.screens.team.fragments.transfer_in.TransferOutPresenter
import com.webbisswift.cfcn.ui.screens.team.fragments.transfer_out.TransferOutContract
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by biswas on 22/12/2017.
 */

class TransferOutFragment: BaseFragment(), TransferOutContract.TransferOutView{

    var presenter: TransferOutPresenter? = null


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
        this.presenter = TransferOutPresenter(model)
    }



    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    private fun setupListeners(){
        rvRefresh.setOnRefreshListener {
            this.presenter?.loadTransferOutPlayers()
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

    override fun showLoading() {
        rvRefresh?.postDelayed({
            rvRefresh?.isRefreshing = true
        }, 600)
    }



    override fun addPlayers(players: List<TransferOutPlayer>) {

        for(player in players) {
            val playerItem = SquadItem(SquadAdapterConstants.TYPE_TRANSFER_OUT, null, null, null,  null, null , player)
            this.rvAdapter.addSquadItem(playerItem)
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