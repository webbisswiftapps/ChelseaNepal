package com.webbisswift.cfcn.ui.screens.home.fragments.matches

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match

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