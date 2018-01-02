package com.webbisswift.cfcn.ui.screens.match_center.fragments.lineups

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.MCLiveTickerContract

/**
 * Created by biswas on 01/01/2018.
 */


class MCLineupPresenter(val model: MatchCenterModel): MCLineupContract.MCLineupPresenter {

    var view: MCLineupContract.MCLineupView? = null


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/


    override fun loadNextMatchInfo() {
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val lmInfo = dataSnapshot.getValue<Match>(Match::class.java)
                    presentNextMatchInfo(lmInfo)
                } catch (e: Exception) {
                    e.printStackTrace()
                    view?.showLineupError("Lineups not available.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.showLineupError("Lineups not available.")
            }
        }
        this.model.subscribeToNextMatch(nextMatchListener)


    }

    /**
     * Presentation Methods
     **/

    fun presentNextMatchInfo(match: Match?) {


        if (match != null  ) {

            val matchFacts = match.live?.match_facts
            if(matchFacts != null && matchFacts.lineup != null){
                val isChelsea = matchFacts.facts?.localteam_name?.toLowerCase()?.contentEquals("chelsea")
                if(isChelsea!=null && isChelsea){
                    //chelsea is home team
                    view?.showLineupsMain()
                    view?.setLineupChelsea(matchFacts.lineup.localteam, matchFacts.subs.localteam)
                    view?.setLineupOpponent(matchFacts.lineup.visitorteam, matchFacts.subs.visitorteam, matchFacts.facts.visitorteam_name)
                }else{
                    view?.showLineupsMain()
                    //chelsea is away team
                    view?.setLineupChelsea(matchFacts.lineup.visitorteam, matchFacts.subs.visitorteam)
                    view?.setLineupOpponent(matchFacts.lineup.localteam,matchFacts.subs.localteam, matchFacts.facts.localteam_name)
                }
            }else view?.showLineupError("Lineups not available.")


        } else view?.showLineupError("Lineups not available.")
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
        this.view = view as MCLineupContract.MCLineupView
        loadNextMatchInfo()
    }




}