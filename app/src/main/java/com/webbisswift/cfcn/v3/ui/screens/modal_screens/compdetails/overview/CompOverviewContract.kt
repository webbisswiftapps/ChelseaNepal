package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.overview

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem

/**
 * Created by apple on 2/12/18.
 */


interface  CompOverviewContract{

    interface CompOverviewView : BaseView {


        fun setUpcomingMatch(match:SMMatch)
        fun hideUpcoming()

        fun setLatestMatch(match:SMMatch)
        fun hideLatest()


        fun showTableLoading()
        fun hideTableLoading()
        fun setTableTitle(title:String)
        fun addTeamToTable(team: SMStandingItem, isChelsea:Boolean)
        fun hideTableCard()
        fun showTableCard()
        fun clearTable()



    }

    interface  CompOverviewPresenter: BasePresenter {
        fun loadMatches()
        fun loadStanding()
    }

    interface CompOverviewModel{
        fun subscribeToMatches(listener: ValueEventListener)
        fun subscribeToStandings(listener: ValueEventListener)
        fun unsubscribe()
    }

}