package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadContract

/**
 * Created by apple on 2/10/18.
 */

class TeamChartsPresenter(val model:SquadContract.SquadModel):TeamChartsContract.TeamChartsPresenter{
    var view: TeamChartsContract.TeamChartsView? = null


    override fun loadSquad() {
        this.view?.setTeamSquadLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<SMSquad>(SMSquad::class.java)
                    presentTeamInfo(teamInfo)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showSquadNotAvailable()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToSquad(nextMatchListener)
    }


    fun presentTeamInfo(squad: SMSquad?){
        view?.hideTeamSquadLoading()
        if(squad != null && squad.data.size > 0){
            //2. add Players by group
            view?.setTeamSquad(squad.data)
        }else view?.showSquadNotAvailable()
    }


    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as TeamChartsContract.TeamChartsView
        loadSquad()
    }

    override fun detachView() {
        this.model.unsubscribe()
        this.view = null
    }
}