package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.season

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem

/**
 * Created by apple on 12/14/17.
 */

interface SeasonContract{

    interface SeasonView{

        fun showFormGuideLoading()
        fun hideFormGuideLoading()
        fun addFormResult(type:String)
        fun clearFormPane()
        fun hideFormPane()



        fun showTableLoading()
        fun hideTableLoading()
        fun addTeamToTable(team:SMStandingItem, isChelsea:Boolean)
        fun hideTableCard()
        fun showTableCard()
        fun clearTable()

        fun showChartsLoading()
        fun hideChartsLoading()
        fun setTopScorer(player: SMSquad.SMSquadItem)
        fun setTopAssister(player: SMSquad.SMSquadItem)
        fun setTopYC(player: SMSquad.SMSquadItem)
        fun setTopAppearance(player: SMSquad.SMSquadItem)
        fun setTopRC(player: SMSquad.SMSquadItem)
        fun hideCharts()

    }


    interface SeasonPresenter : BasePresenter {
        fun loadFormGuideInfo()
        fun loadLeagueTable()
        fun loadTopCharts()
    }

    interface SeasonModel{
        fun subscribeToFormGuide(listener: ValueEventListener)
        fun subscribeToLeagueTable(listener:ValueEventListener)
        fun subscribeToSquad(listener:ValueEventListener)
        fun unsubscribeFromFirebase()
    }

}