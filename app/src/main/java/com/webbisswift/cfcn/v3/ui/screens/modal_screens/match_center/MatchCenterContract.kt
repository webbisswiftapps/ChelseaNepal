package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch


/**
 * Created by apple on 12/31/17.
 */

interface MatchCenterContract{

    interface MatchCenterView : BaseView {
        fun setNextMatchAwayTeam(name:String, logo:String)
        fun setNextMatchHomeTeam(name:String, logo:String)
        fun setNextMatchCompetitionName(name:String)
        fun setNextMatchVenue(venue:String)
        fun setNextMatchDate(date:String, blink:Boolean)
        fun setNextMatchPenalties(homePenalties:String, awayPenalties:String)
        fun setNextMatchScore(homeScore:String, awayScore:String)
        fun setNextMatchTVGuide(match:SMMatch)
        fun prepareMatchCenterTabs(isStarted:Boolean)
        fun showNoDataAndFinish()
    }

    interface  MatchCenterPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}