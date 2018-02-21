package com.webbisswift.cfcn.ui.screens.modal.match_center

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMConstants
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.utils.Utilities


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

            this.view?.setNextMatchHomeTeam(SMTeamShort.getTeamShort(match.localTeam.data.name), match.localTeam.data.logo_path)
            this.view?.setNextMatchAwayTeam(SMTeamShort.getTeamShort(match.visitorTeam.data.name), match.visitorTeam.data.logo_path)
            this.view?.setNextMatchCompetitionName(match.competitionDesc)

            if(match.venue != null) {
                this.view?.setNextMatchVenue(match.venue.data.name)
            }else{
                if(match.localteam_id == SMConstants.CHELSEA_TEAM_ID)
                    this.view?.setNextMatchVenue("Stamford Bridge")
                else this.view?.setNextMatchVenue("Away")
            }

            val startDT = match.time.starting_at.startDateTime
            if(startDT!= null) {
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)


                if (timeDiff <= 0 || match.time.isLive) {

                    val statusDesc = match.time.statusDescription

                    this.view?.setNextMatchDate(statusDesc, match.time.isLive)
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
        this.view = null
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as MatchCenterContract.MatchCenterView
        loadNextMatchInfo()

    }



}