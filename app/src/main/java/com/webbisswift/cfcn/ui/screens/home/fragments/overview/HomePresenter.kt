package com.webbisswift.cfcn.ui.screens.home.fragments.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.*
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.utils.Utilities

/**
 * Created by apple on 12/3/17.
 */

class HomePresenter(val model:HomeModel):HomeContract.HomePresenter{

    var view:HomeContract.HomeView? = null




     /**
      * Non Lifecycle Methods : Load Info methods
      * **/
    override fun loadNextMatchInfo() {
         this.view?.showNextMatchInfoLoading()
         val nextMatchListener = object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {

                 try {
                     val nextMatchInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
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
    fun presentNextMatchInfo(match: SMMatch?){
        if(match != null) {
            this.view?.hideNextMatchInfoLoading()

            val localTeamId = match.localteam_id
            if(localTeamId == 18){
                val name = match.visitorTeam.data.name
                val logo = match.visitorTeam.data.logo_path
                this.view?.setNextMatchAwayTeam(name, logo)
            }else{
                val name = match.localTeam.data.name
                val logo = match.localTeam.data.logo_path
                this.view?.setNextMatchAwayTeam(name, logo)
            }

            this.view?.setNextMatchScoreHomeTeam(match.localTeam.data.name, match.localTeam.data.logo_path)
            this.view?.setNextMatchScoreAwayTeam(match.visitorTeam.data.name, match.visitorTeam.data.logo_path)

            this.view?.setCompetitionName(match.competitionDesc)
            this.view?.setNextMatchVenue(match.venue.data.name)


            val startDT = match.time.starting_at.startDateTime
            if(startDT!= null){
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)
                this.model.setNextMatchAlarm(startDT, match.localTeam.data.name, match.visitorTeam.data.name)

                this.view?.setNextMatchTimings(Utilities.getLocaleFormattedDate(startDT))

                if(timeDiff <= 0 || match.time.isLive){
                    this.view?.switchToScores()

                    val statusDesc = match.time.statusDescription

                    this.view?.setMatchStatus(statusDesc, match.time.isLive)
                    this.view?.setMatchHomeScore(match.scores.localteam_score)
                    this.view?.setMatchAwayScore(match.scores.visitorteam_score)

                    if(match.time.showPenalties()){
                        this.view?.setNextMatchPenalties(match.scores.localteam_pen_score, match.scores.visitorteam_pen_score)
                    }else view?.hideNextMatchPenalties()

                }else{
                    this.view?.switchToCountdown()
                    this.view?.startNextMatchCountdown(timeDiff)
                }

            }else view?.hideNextMatchCard()


            if(match.tv_guide_all != null) {
                var tv = match.tv_guide_all[model.getUserCountry()]
                if (tv == null || tv.isBlank()) {
                    tv = match.tv_guide_all["International"]
                }

                if (tv != null && tv.isNotBlank()) {
                    this.view?.setTVInfo(tv)
                } else this.view?.setTVInfo("Not Available.")
            }else this.view?.setTVInfo("Not Available.")


        }else this.view?.hideNextMatchCard()
    }



    fun presentLastMatchInfo(match:Match?){
        if(match!=null){
            this.view?.hideLastMatchInfoLoading()

            this.view?.setLastMatchHomeTeam(match.home, match.homeShirtURL)
            this.view?.setLastMatchAwayTeam(match.away, match.awayShirtURL)
            this.view?.setLastMatchCompetitionName(match.competition)
            this.view?.setLastMatchDate(match.start_date)
            this.view?.setLastMatchScore(match.homeScore, match.awayScore)
            if(match.hadPenalties())
                this.view?.setLastMatchPenalties(match.homePenaltyScore, match.awayPenaltyScore)

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