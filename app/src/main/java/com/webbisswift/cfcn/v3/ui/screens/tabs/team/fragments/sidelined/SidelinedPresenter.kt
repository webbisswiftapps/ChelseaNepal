package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMSidelined

/**
 * Created by biswas on 22/12/2017.
 */

class SidelinedPresenter(val model: SidelinedContract.SidelinedModel) : SidelinedContract.SidelinedPresenter{

    var view: SidelinedContract.SidelinedView? = null

    override fun loadSidelinedPlayers() {
        this.view?.showSidelinedLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<SMSidelined>(SMSidelined::class.java)
                    presentTeamInfo(teamInfo)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoSidelined()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToSidelined(nextMatchListener)
    }

    fun presentTeamInfo(sidelined: SMSidelined?){
        view?.hideSidelinedLoading()
        if(sidelined != null){
                view?.clearList()
                view?.addPlayers(sidelined.data)
        }else view?.showNoSidelined()
    }

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as SidelinedContract.SidelinedView
        loadSidelinedPlayers()
    }

    override fun detachView() {
        this.view = null
        this.model.unsubscribeFromSidelined()
    }
}