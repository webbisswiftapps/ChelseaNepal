package com.webbisswift.cfcn.ui.screens.match_center

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat

/**
 * Created by apple on 12/31/17.
 */

interface MatchCenterContract{

    interface MatchCenterView : BaseView {
        fun setNextMatchAwayTeam(name:String, logo:String)
        fun setNextMatchHomeTeam(name:String, logo:String)
        fun setNextMatchCompetitionName(name:String)
        fun setNextMatchDate(date:String)
        fun setNextMatchPenalties(homePenalties:String, awayPenalties:String)
        fun setNextMatchScore(homeScore:String, awayScore:String)
        fun setCurrentMatchStatus(status:String)
        fun showNoDataAndFinish()
    }

    interface  MatchCenterPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}