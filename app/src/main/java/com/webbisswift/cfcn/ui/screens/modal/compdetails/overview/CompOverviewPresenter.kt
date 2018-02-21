package com.webbisswift.cfcn.ui.screens.modal.compdetails.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMConstants
import com.webbisswift.cfcn.domain.model.v2.SMLeagueMatches
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem

/**
 * Created by apple on 2/12/18.
 */

class CompOverviewPresenter(val model:CompOverviewContract.CompOverviewModel):CompOverviewContract.CompOverviewPresenter{

    var view: CompOverviewContract.CompOverviewView? = null

    override fun loadStanding() {

        this.view?.showTableLoading()
        val tableListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMStandingItem>>() {}
                    val tableItems: List<SMStandingItem>? = dataSnapshot.getValue(t)
                    presentStandings(tableItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideTableCard()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.hideTableCard()
            }
        }
        this.model.subscribeToStandings(tableListener)
    }


    override fun loadMatches() {

        this.view?.hideLatest()
        this.view?.hideUpcoming()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val matches = dataSnapshot.getValue<SMLeagueMatches>(SMLeagueMatches::class.java)
                    presentMatches(matches)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideLatest()
                    view?.hideUpcoming()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.hideLatest()
                view?.hideUpcoming()
            }
        }
        this.model.subscribeToMatches(nextMatchListener)
    }


    private fun presentStandings(table:List<SMStandingItem>?){
        view?.hideTableLoading()
        view?.clearTable()
        if(table!=null && table.count() > 0){
            val i = table[0]

            if(i.group_name != null){
                view?.setTableTitle(i.group_name)
            }else view?.setTableTitle("Standings")

            for(item in table){
                val isChelsea = (item.team_id == SMConstants.CHELSEA_TEAM_ID)
                view?.addTeamToTable(item, isChelsea)
            }

        }else view?.hideTableCard()
    }


    private fun presentMatches(matches:SMLeagueMatches?){
        if(matches != null){
            val latest = matches.last
            val upcoming = matches.next

            if(latest != null)
                view?.setLatestMatch(latest)
            else view?.hideLatest()

            if(upcoming != null)
                view?.setUpcomingMatch(upcoming)
            else view?.hideUpcoming()
        }
    }


    override fun resume() {
    }


    override fun pause() {
    }

    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as CompOverviewContract.CompOverviewView
        loadStanding()
        loadMatches()
    }


    override fun detachView() {
        this.view = null
        model.unsubscribe()
    }






}