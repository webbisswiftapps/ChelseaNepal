package com.webbisswift.cfcn.ui.screens.modal.compdetails.leaderboard

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMLeagueCharts

/**
 * Created by apple on 2/12/18.
 */

interface  LeaderboardContract{

    interface View : BaseView {


        fun showLoading()

        fun setTopGS(items:List<SMLeagueCharts.GoalItem>)
        fun hideTopGSCard()

        fun setTopCS(items:List<SMLeagueCharts.CardItem>)
        fun hideTopCSCard()

        fun setTopAS(items:List<SMLeagueCharts.AssistItem>)
        fun hideTopASCard()

        fun hideLoading()
        fun showNoDataError()

    }

    interface  Presenter: BasePresenter {
        fun loadCharts()
    }

    interface Model{
        fun subscribeToCharts(listener: ValueEventListener)
        fun unsubscribe()
    }

}