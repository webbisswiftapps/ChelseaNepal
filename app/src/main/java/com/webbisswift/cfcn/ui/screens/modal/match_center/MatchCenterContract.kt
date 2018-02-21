package com.webbisswift.cfcn.ui.screens.modal.match_center

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView

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
        fun showNoDataAndFinish()
    }

    interface  MatchCenterPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}