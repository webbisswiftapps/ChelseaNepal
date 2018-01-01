package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat

/**
 * Created by apple on 12/31/17.
 */


interface  MCOverviewContract{

    interface MCOverviewView : BaseView {

        fun showLastMatchFactsNotFound()
        fun showLastMatchStats(homeStats: MatchStat, awayStat: MatchStat)

        fun hideLastMatchFactsLoading()

        fun addMatchEvent(event: FactsMatchEvent)
        fun showMatchEventsCard()
        fun hideMatchEventsCard()

        fun setTvGuide(guide:String)

        fun showMatchNotStarted()

    }

    interface  MCOverviewPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}