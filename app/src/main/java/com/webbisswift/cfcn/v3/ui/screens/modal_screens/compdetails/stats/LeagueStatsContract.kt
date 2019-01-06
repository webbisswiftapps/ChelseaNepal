package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.stats

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMTeamLeagueStats

/**
 * Created by apple on 2/12/18.
 */


class LeagueStatsContract{

    interface View : BaseView {


        fun showLoading()
        fun hideLoading()
        fun setStatistics(stats:SMTeamLeagueStats)
        fun showNoStatsError()

    }

    interface  Presenter: BasePresenter {
        fun loadStats()
    }

    interface Model{
        fun subscribeToStats(listener: ValueEventListener)
        fun unsubscribe()
    }


}