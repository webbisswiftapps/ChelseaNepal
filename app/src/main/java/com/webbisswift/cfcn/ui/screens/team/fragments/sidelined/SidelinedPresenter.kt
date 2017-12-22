package com.webbisswift.cfcn.ui.screens.team.fragments.sidelined

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.SquadPlayer
import com.webbisswift.cfcn.domain.model.TeamInfo
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel
import com.webbisswift.cfcn.ui.screens.team.fragments.squad.SquadContract

/**
 * Created by biswas on 22/12/2017.
 */

class SidelinedPresenter(val model: TeamInfoModel) : SidelinedContract.SidelinedPresenter{

    var view: SidelinedContract.SidelinedView? = null

    override fun loadSidelinedPlayers() {
        this.view?.showSidelinedLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<TeamInfo>(TeamInfo::class.java)
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
        this.model.subscribeToTeamInfo(nextMatchListener)
    }

    fun presentTeamInfo(teamInfo: TeamInfo?){
        view?.hideSidelinedLoading()
        if(teamInfo != null){
            val sidelined = teamInfo.sidelined
            if(sidelined != null){
                view?.addPlayers(sidelined)
            }else view?.showNoSidelined()
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
        this.model.unsubscribeFromTeamInfo()
    }
}