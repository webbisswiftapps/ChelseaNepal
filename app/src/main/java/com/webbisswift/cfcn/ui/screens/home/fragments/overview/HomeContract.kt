package com.webbisswift.cfcn.ui.screens.home.fragments.overview

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.LeagueTableItem
import com.webbisswift.cfcn.domain.model.MatchEvent
import java.util.*

/**
 * Created by apple on 12/3/17.
 */
interface HomeContract {

    interface HomeView : BaseView{

        fun showNextMatchInfoLoading()
        fun setNextMatchAwayTeam(name:String, logo:String)

        fun setCompetitionName(competition:String)
        fun setTVInfo(tvInfo:String)
        fun startNextMatchCountdown(seedTime:Long)
        fun setNextMatchTimings(timings:String)
        fun hideNextMatchInfoLoading()
        fun hideNextMatchCard()
        fun switchToScores()
        fun switchToCountdown()
        fun setMatchHomeScore(score:String)
        fun setMatchAwayScore(score:String)
        fun setNextMatchVenue(venue:String)
        fun setNextMatchScoreHomeTeam(name:String, logo:String)
        fun setNextMatchScoreAwayTeam(name:String, logo:String)
        fun setMatchStatus(status:String, blink:Boolean)
        fun setNextMatchPenalties(homePenalties: String, awayPenalties: String)
        fun hideNextMatchPenalties()



        fun showLastMatchInfoLoading()
        fun setLastMatchAwayTeam(name:String, logo:String)
        fun setLastMatchHomeTeam(name:String, logo:String)
        fun setLastMatchCompetitionName(name:String)
        fun setLastMatchDate(date:String)
        fun setLastMatchPenalties(homePenalties:String, awayPenalties:String)
        fun setLastMatchScore(homeScore:String, awayScore:String)
        fun hideLastMatchInfoLoading()
        fun hideLastMatchCard()





        fun showTeamStatsLoading()
        fun hideTeamStatsLoading()
        fun showTeamStats(item:LeagueTableItem)
        fun hideTeamStatsPane()

    }


    interface HomePresenter : BasePresenter{
        fun loadNextMatchInfo()
        fun loadLastMatchInfo()
        fun loadEPLStats()
    }

    interface HomeModel{
        fun subscribeToNextMatch(listener:ValueEventListener)
        fun subscribeToLastMatch(listener:ValueEventListener)
        fun subscribeToEPLStats(listener:ValueEventListener)
        fun setNextMatchAlarm(startDateTime:Date, home:String, away:String)
        fun unsubscribeFromFirebase()
        fun getUserCountry():String
    }


}