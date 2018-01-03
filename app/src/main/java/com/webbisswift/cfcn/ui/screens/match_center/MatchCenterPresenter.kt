package com.webbisswift.cfcn.ui.screens.match_center

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.screens.match_facts.MatchFactsContract
import com.webbisswift.cfcn.utils.Utilities
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.LocalDateTime

/**
 * Created by apple on 12/31/17.
 */


class MatchCenterPresenter(val model: MatchCenterModel): MatchCenterContract.MatchCenterPresenter{

    var view: MatchCenterContract.MatchCenterView? = null




    /**
     * Non Lifecycle Methods : Load Info methods
     * **/


    override fun loadNextMatchInfo() {
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val lmInfo = dataSnapshot.getValue<Match>(Match::class.java)
                    presentNextMatchInfo(lmInfo)
                }catch (e:Exception){
                    e.printStackTrace()
                    view?.showNoDataAndFinish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToNextMatch(nextMatchListener)


    }

    /**
     * Presentation Methods
     **/

    fun presentNextMatchInfo(match: Match?){
        if(match!=null){
            this.view?.setNextMatchHomeTeam(match.home, match.homeShirtURL)
            this.view?.setNextMatchAwayTeam(match.away, match.awayShirtURL)
            this.view?.setNextMatchCompetitionName(match.competition)

            if(match.live.isStarted) {
                this.view?.setCurrentMatchStatus(match.live.status)
                this.view?.setNextMatchDate("LIVE")
                this.view?.setNextMatchScore(match.live.homeScore, match.live.awayScore)
            }else{
                this.view?.setCurrentMatchStatus(":")

                this.view?.setNextMatchDate(Utilities.getLocaleFormattedDate(match.startDateTime))
                this.view?.setNextMatchScore("-", "-")
            }

        }else view?.showNoDataAndFinish()
    }

    /**
     * LifeCycle Methods
     */

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
        this.model.unsubscribeFromNextMatch()
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as MatchCenterContract.MatchCenterView
        loadNextMatchInfo()

    }



}