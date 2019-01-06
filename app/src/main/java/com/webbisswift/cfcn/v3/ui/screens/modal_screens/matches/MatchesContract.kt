package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch

/**
 * Created by apple on 12/7/17.
 */
interface MatchesContract {

    interface MatchesView : BaseView {
        fun showLoading()
        fun addMatches(fixtures:List<SMMatch>, scrollTo:Int)
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