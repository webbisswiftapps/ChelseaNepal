package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.leaderboard

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMLeagueCharts


/**
 * Created by apple on 2/12/18.
 */

class LeaderboardPresenter(val model: LeaderboardContract.Model): LeaderboardContract.Presenter{

    var view: LeaderboardContract.View? = null



    override fun loadCharts() {

        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val matches = dataSnapshot.getValue<SMLeagueCharts>(SMLeagueCharts::class.java)
                    presentCharts(matches)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoDataError()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.showNoDataError()
            }
        }
        this.model.subscribeToCharts(nextMatchListener)
    }



    private fun presentCharts(charts: SMLeagueCharts?){

        if(charts != null){
            view?.hideLoading()

            if(charts.goals!= null && !charts.goals.isEmpty())
                view?.setTopGS(charts.goals.take(5))

            if(charts.assist!= null && !charts.assist.isEmpty())
                view?.setTopAS(charts.assist.take(5))

            if(charts.cards!= null && !charts.cards.isEmpty())
                view?.setTopCS(charts.cards.take(5))

        }else view?.showNoDataError()
    }


    override fun resume() {
    }


    override fun pause() {
    }

    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as LeaderboardContract.View
        loadCharts()
    }


    override fun detachView() {
        this.view = null
        model.unsubscribe()
    }


}