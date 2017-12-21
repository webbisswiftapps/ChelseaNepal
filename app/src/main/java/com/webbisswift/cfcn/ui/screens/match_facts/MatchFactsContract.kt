package com.webbisswift.cfcn.ui.screens.match_facts

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Facts
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat

/**
 * Created by apple on 12/20/17.
 */

interface  MatchFactsContract{

    interface MatchFactsView : BaseView {
        fun setLastMatchAwayTeam(name:String, logo:String)
        fun setLastMatchHomeTeam(name:String, logo:String)
        fun setLastMatchCompetitionName(name:String)
        fun setLastMatchDate(date:String)
        fun setLastMatchPenalties(homePenalties:String, awayPenalties:String)
        fun setLastMatchScore(homeScore:String, awayScore:String)
        fun showLastMatchFactsNotFound()
        fun showLastMatchStats(homeStats:MatchStat, awayStat: MatchStat)
        fun hideLastMatchFactsLoading()
        fun addMatchEvent(event:FactsMatchEvent)
        fun hideMatchEventsCard()

        fun showNoDataAndFinish()

    }

    interface  MatchFactsPresenter:BasePresenter{
        fun loadLastMatchInfo()
    }

    interface  MatchFactsModel{
        fun subscribeToLastMatch(listener:ValueEventListener)
        fun unsubscribeFromFirebase()
    }

}