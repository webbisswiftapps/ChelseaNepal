package com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.MatchComment
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.adapter.LiveTickerAdapter
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel
import com.webbisswift.cfcn.ui.screens.team.adapters.SquadRVAdapter
import com.webbisswift.cfcn.ui.screens.team.fragments.transfer_in.TransferOutPresenter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * Created by apple on 12/31/17.
 */

class MCLiveTickerFragment:BaseFragment(), MCLiveTickerContract.MCLiveTickerView{


    var presenter: MCLiveTickerContract.MCLiveTickerPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rv, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }


    private lateinit var rvAdapter: LiveTickerAdapter
    private fun setupRecyclerView(){


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        rv.addItemDecoration(dividerItemDecoration)

        rvAdapter = LiveTickerAdapter(this.context)
        rv.adapter = rvAdapter

    }

    override fun showTickerNotStarted(error:String) {
        if(rvVS?.currentView?.id == R.id.rv){
            rvVS.showNext()
            errorRV.text = error
        }
    }

    override fun setComments(comments: List<MatchComment>) {
        if(rvVS?.currentView?.id != R.id.rv){
            rvVS.showPrevious()
        }
        this.rvAdapter.addComments(comments)
    }



    override fun initView() {
        val model = MatchCenterModel(FirebaseDatabase.getInstance())
        this.presenter = MCLiveTickerPresenter(model)
    }

    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


}