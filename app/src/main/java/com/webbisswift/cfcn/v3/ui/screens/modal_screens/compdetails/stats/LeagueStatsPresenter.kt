package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.stats

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMTeamLeagueStats

/**
 * Created by apple on 2/12/18.
 */


class LeagueStatsPresenter(val model: LeagueStatsContract.Model): LeagueStatsContract.Presenter{

    var view: LeagueStatsContract.View? = null



    override fun loadStats() {

        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val matches = dataSnapshot.getValue<SMTeamLeagueStats>(SMTeamLeagueStats::class.java)
                    presentStats(matches)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoStatsError()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.showNoStatsError()
            }
        }
        this.model.subscribeToStats(nextMatchListener)
    }



    private fun presentStats(stats: SMTeamLeagueStats?){

        if(stats != null){
            view?.hideLoading()
            view?.setStatistics(stats)
        }else view?.showNoStatsError()

    }


    override fun resume() {
    }


    override fun pause() {
    }

    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as LeagueStatsContract.View
        loadStats()
    }


    override fun detachView() {
        this.view = null
        model.unsubscribe()
    }


}