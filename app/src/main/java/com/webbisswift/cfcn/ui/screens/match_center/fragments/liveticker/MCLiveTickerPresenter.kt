package com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.match_center.fragments.overview.MCOverviewContract

/**
 * Created by apple on 12/31/17.
 */


class MCLiveTickerPresenter(val model: MatchCenterModel): MCLiveTickerContract.MCLiveTickerPresenter {

    var view: MCLiveTickerContract.MCLiveTickerView? = null


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
                    view?.showTickerNotStarted("Live ticker unavailable.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.showTickerNotStarted("Live ticker unavailable.")
            }
        }
        this.model.subscribeToNextMatch(nextMatchListener)


    }

    /**
     * Presentation Methods
     **/

    fun presentNextMatchInfo(match: Match?) {


        if (match != null && (match.live.isStarted || match.live.status == "FT") ) {

            val matchFacts = match.live.match_facts
            if(matchFacts != null){

                val ticker = matchFacts.comments
                if(ticker!=null && ticker.isNotEmpty()){
                    view?.setComments(ticker)
                }else view?.showTickerNotStarted( "Live ticker will start shortly.")
            }else view?.showTickerNotStarted("Live ticker will start shortly.")

        } else view?.showTickerNotStarted("Match has not started yet.")
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
        this.view = view as MCLiveTickerContract.MCLiveTickerView
        loadNextMatchInfo()

    }


}