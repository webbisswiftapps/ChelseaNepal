package com.webbisswift.cfcn.ui.screens.match_center

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.domain.model.v2.SMMatch
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
                    val lmInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
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

    fun presentNextMatchInfo(match: SMMatch?){
        if(match!=null){

            this.view?.setNextMatchHomeTeam(match.localTeam.data.name, match.localTeam.data.logo_path)
            this.view?.setNextMatchAwayTeam(match.visitorTeam.data.name, match.visitorTeam.data.logo_path)
            this.view?.setNextMatchCompetitionName(match.competitionDesc)
            this.view?.setNextMatchVenue(match.venue.data.name)

            val startDT = match.time.starting_at.startDateTime
            if(startDT!= null) {
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)


                if (timeDiff <= 0 || match.time.isLive) {

                    val statusDesc = match.time.statusDescription

                    this.view?.setNextMatchDate(statusDesc, true)
                    this.view?.setNextMatchScore(match.scores.localteam_score, match.scores.visitorteam_score)


                    if (match.time.showPenalties())
                        this.view?.setNextMatchPenalties(match.scores.localteam_pen_score, match.scores.visitorteam_pen_score)

                } else {
                    val startDT = match.time.starting_at.startDateTime
                    this.view?.setNextMatchDate(Utilities.getLocaleFormattedDate(startDT), false)
                    this.view?.setNextMatchScore("-", "-")
                }
            }else view?.showNoDataAndFinish()

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