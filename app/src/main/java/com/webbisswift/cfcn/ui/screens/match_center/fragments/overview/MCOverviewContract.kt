package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch


/**
 * Created by apple on 12/31/17.
 */


interface  MCOverviewContract{

    interface MCOverviewView : BaseView {

        //fun showLastMatchFactsNotFound()
        //fun showLastMatchStats(homeStats: MatchStat, awayStat: MatchStat)

        //fun hideLastMatchFactsLoading()

        //fun addMatchEvent(event: FactsMatchEvent)
        //fun showMatchEventsCard()
        //fun hideMatchEventsCard()
        //  fun showMatchNotStarted()


        fun setTvGuide(guide:String)
        fun setWeather(condition:String, temperature:String, conditionURL:String)
        fun hideWeather()

        fun setHeadToHead(h2h: List<SMMatch>)
        fun hideHeadToHead()



    }

    interface  MCOverviewPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}