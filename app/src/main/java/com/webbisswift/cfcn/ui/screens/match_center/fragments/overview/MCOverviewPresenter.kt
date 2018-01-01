package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel

/**
 * Created by apple on 12/31/17.
 */

class MCOverviewPresenter(val model: MatchCenterModel): MCOverviewContract.MCOverviewPresenter {

    var view: MCOverviewContract.MCOverviewView? = null


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
                    view?.showMatchNotStarted()
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

    fun presentNextMatchInfo(match: Match?) {


        if (match != null) {

            view?.setTvGuide(match.tv_guide)

            if(match.live.match_facts != null){
                val events = match.live.match_facts.facts.events
                if(events != null && events.size > 0){
                    view?.showMatchEventsCard()
                    for(event in events.reversed()){
                        view?.addMatchEvent(event)
                    }
                }else this.view?.hideMatchEventsCard()

                val stats = match.live.match_facts.match_stats
                if(stats != null){
                    this.view?.showLastMatchStats(stats.localteam, stats.visitorteam)
                }

            }else this.view?.showLastMatchFactsNotFound()
        } else view?.showMatchNotStarted()
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
        this.view = view as MCOverviewContract.MCOverviewView
        loadNextMatchInfo()

    }


}