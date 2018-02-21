package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.stats

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMStats

/**
 * Created by biswas on 08/02/2018.
 */


interface  MCStatsContract{

    interface MCStatsView : BaseView {

        fun showMatchNotStarted()
        fun showMatchStatsUnavailable()
        fun setMatchStats(home:SMStats.StatsData, away:SMStats.StatsData)


    }

    interface  MCStatsPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}