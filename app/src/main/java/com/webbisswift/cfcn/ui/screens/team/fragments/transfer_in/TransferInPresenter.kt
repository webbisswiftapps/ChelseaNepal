package com.webbisswift.cfcn.ui.screens.team.fragments.transfer_in

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.TeamInfo
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel
import com.webbisswift.cfcn.ui.screens.team.fragments.sidelined.SidelinedContract

/**
 * Created by biswas on 22/12/2017.
 */

class TransferInPresenter(val model: TeamInfoModel) : TransferInContract.TransferInPresenter{

    var view: TransferInContract.TransferInView? = null

    override fun loadTransferInPlayers() {
        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<TeamInfo>(TeamInfo::class.java)
                    presentTeamInfo(teamInfo)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoPlayers()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToTeamInfo(nextMatchListener)
    }

    fun presentTeamInfo(teamInfo: TeamInfo?){
        view?.hideLoading()
        if(teamInfo != null){
            val tin = teamInfo.transfers_in
            if(tin != null){
                view?.clearList()
                view?.addPlayers(tin)
            }else view?.showNoPlayers()
        }else view?.showNoPlayers()
    }

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as TransferInContract.TransferInView
        loadTransferInPlayers()
    }

    override fun detachView() {
        this.model.unsubscribeFromTeamInfo()
    }
}