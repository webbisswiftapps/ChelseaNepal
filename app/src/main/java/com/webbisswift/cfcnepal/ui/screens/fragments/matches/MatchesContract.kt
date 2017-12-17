package com.webbisswift.cfcnepal.ui.screens.fragments.matches

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcnepal.base.BasePresenter
import com.webbisswift.cfcnepal.base.BaseView
import com.webbisswift.cfcnepal.domain.model.LeagueTableItem
import com.webbisswift.cfcnepal.domain.model.Match
import com.webbisswift.cfcnepal.domain.model.TopScorer

/**
 * Created by apple on 12/7/17.
 */
interface MatchesContract {

    interface MatchesView : BaseView {
        fun showLoading()
        fun addFixtures(fixtures:List<Match>)
        fun addResults(results:List<Match>)
        fun hideLoading()
    }


    interface MatchesPresenter : BasePresenter {
        fun loadFixtures()
        fun loadResults()
    }

    interface MatchesModel{
        fun subscribeToFixtures(listener:ValueEventListener)
        fun subscribeToResults(listener: ValueEventListener)
    }


}