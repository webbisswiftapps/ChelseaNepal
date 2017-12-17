package com.webbisswift.cfcnepal.ui.screens.fragments.season

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcnepal.base.BasePresenter
import com.webbisswift.cfcnepal.domain.model.LeagueTableItem
import com.webbisswift.cfcnepal.domain.model.TopScorer
import com.webbisswift.cfcnepal.ui.screens.fragments.home.HomeContract
import java.util.*

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

        fun showTopScorersLoading()
        fun hideTopScorersLoading()
        fun addTopSocrer(type: TopScorer)
        fun clearTopScorerPane()
        fun hideTopScorerPane()

        fun showTableLoading()
        fun hideTableLoading()
        fun addTeamToTable(team:LeagueTableItem, isChelsea:Boolean)
        fun hideTableCard()
        fun showTableCard()
        fun clearTable()


    }


    interface SeasonPresenter : BasePresenter {
        fun loadFormGuideInfo()
        fun loadTopScorers()
        fun loadLeagueTable()
    }

    interface SeasonModel{
        fun subscribeToFormGuide(listener: ValueEventListener)
        fun subscribeToTopScorers(listener: ValueEventListener)
        fun subscribeToLeagueTable(listener:ValueEventListener)
        fun unsubscribeFromFirebase()
    }

}