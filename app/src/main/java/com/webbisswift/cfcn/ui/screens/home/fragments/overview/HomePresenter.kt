package com.webbisswift.cfcn.ui.screens.home.fragments.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.*
import com.webbisswift.cfcn.utils.Utilities
import java.util.ArrayList

/**
 * Created by apple on 12/3/17.
 */

class HomePresenter(val model:HomeModel):HomeContract.HomePresenter, HomeContract.LiveScoreListener{

    var view:HomeContract.HomeView? = null




     /**
      * Non Lifecycle Methods : Load Info methods
      * **/
    override fun loadNextMatchInfo() {
         this.view?.showNextMatchInfoLoading()
         val nextMatchListener = object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {

                 try {
                     val nextMatchInfo = dataSnapshot.getValue<Match>(Match::class.java)
                     presentNextMatchInfo(nextMatchInfo)
                 }catch(e:Exception){
                     e.printStackTrace()
                     view?.hideNextMatchCard()
                 }
             }

             override fun onCancelled(databaseError: DatabaseError) {
                 println("loadPost:onCancelled ${databaseError.toException()}")
             }
         }
         this.model.subscribeToNextMatch(nextMatchListener)


    }

    override fun loadLastMatchInfo() {
        this.view?.showLastMatchInfoLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val lmInfo = dataSnapshot.getValue<Match>(Match::class.java)
                    presentLastMatchInfo(lmInfo)
                }catch (e:Exception){
                    e.printStackTrace()
                    view?.hideLastMatchCard()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToLastMatch(nextMatchListener)

    }



    override fun loadEPLStats() {
        this.view?.showTeamStatsLoading()
        val tableListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards LeagueTableItem>>() {}
                    val tableItems: List<LeagueTableItem>? = dataSnapshot.getValue(t)
                    presentTeamStats(tableItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideTeamStatsPane()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToEPLStats(tableListener)
    }




    /**
     * Presentation Methods
     **/
    fun presentNextMatchInfo(match: Match?){
        if(match != null) {
            this.view?.hideNextMatchInfoLoading()

            var homeAway = ""
            if(!match.away.toLowerCase().contentEquals("chelsea")){
                this.view?.setNextMatchAwayTeam(match.awayfull, match.awayShirtURL)
                homeAway = "Stamford Bridge | "
            }else{

                this.view?.setNextMatchAwayTeam(match.homefull, match.homeShirtURL)
                homeAway = "Away | "

            }

            this.view?.setNextMatchScoreHomeTeam(match.home, match.homeShirtURL)
            this.view?.setNextMatchScoreAwayTeam(match.away, match.awayShirtURL)

            this.view?.setCompetitionName(homeAway+match.competition)

            this.view?.setTVInfo(match.tv_guide)
            this.view?.setNextMatchTimings(match.start_date+"  "+match.start_time)

            if(match.startDateTime != null){
                val timeDiff = Utilities.getTimeDifferenceFromNow(match.startDateTime)
                this.model.setNextMatchAlarm(match.startDateTime, match.home, match.away)

                if(match.live == null || !match.live.isStarted){
                    this.view?.switchToCountdown()
                    this.view?.startNextMatchCountdown(timeDiff)
                }else{
                    this.view?.switchToScores()
                    this.view?.setMatchStatus(match.live.status)
                    this.view?.setMatchHomeScore(match.live.homeScore)
                    this.view?.setMatchAwayScore(match.live.awayScore)
                }
            }else view?.hideNextMatchCard()

        }else this.view?.hideNextMatchCard()
    }

    override fun scoreUpdateEvent(homeScore: String, awayScore: String, status: String) {
        this.view?.switchToScores()
        this.view?.setMatchHomeScore(homeScore)
        this.view?.setMatchAwayScore(awayScore)
        this.view?.setMatchStatus(status)
    }

    fun presentLastMatchInfo(match:Match?){
        if(match!=null){
            this.view?.hideLastMatchInfoLoading()

            this.view?.setLastMatchHomeTeam(match.home, match.homeShirtURL)
            this.view?.setLastMatchAwayTeam(match.away, match.awayShirtURL)
            this.view?.setLastMatchCompetitionName(match.competition)
            this.view?.setLastMatchDate(match.start_date)
            this.view?.setLastMatchScore(match.homeScore, match.awayScore)

        }else view?.hideLastMatchCard()
    }



    fun presentTeamStats(table:List<LeagueTableItem>?){

        view?.hideTeamStatsLoading()
        if(table!=null && table.count() > 0){
            for(item in table){
                if(item.path.contentEquals("chelsea")){
                    view?.showTeamStats(item)
                    break
                }
            }
        }else view?.hideTeamStatsPane()
    }


    /**
     * LifeCycle Methods
     */

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
        this.model?.unsubscribeFromFirebase()
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as HomeContract.HomeView
        loadNextMatchInfo()
        loadLastMatchInfo()
        loadEPLStats()
    }



}