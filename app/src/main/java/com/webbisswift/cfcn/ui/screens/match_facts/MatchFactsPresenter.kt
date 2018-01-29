package com.webbisswift.cfcn.ui.screens.match_facts

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/20/17.
 */

class MatchFactsPresenter(val model: MatchFactsModel): MatchFactsContract.MatchFactsPresenter{

    var view: MatchFactsContract.MatchFactsView? = null




    /**
     * Non Lifecycle Methods : Load Info methods
     * **/


    override fun loadLastMatchInfo() {
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val lmInfo = dataSnapshot.getValue<Match>(Match::class.java)
                    presentLastMatchInfo(lmInfo)
                }catch (e:Exception){
                    e.printStackTrace()
                    view?.showNoDataAndFinish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToLastMatch(nextMatchListener)


    }

    /**
     * Presentation Methods
     **/

    fun presentLastMatchInfo(match: Match?){
        if(match!=null){
            this.view?.hideLastMatchFactsLoading()

            this.view?.setLastMatchHomeTeam(match.home, match.homeShirtURL)
            this.view?.setLastMatchAwayTeam(match.away, match.awayShirtURL)
            this.view?.setLastMatchCompetitionName(match.competition)
            this.view?.setLastMatchDate(match.start_date)
            this.view?.setLastMatchScore(match.homeScore, match.awayScore)

            if(match.hadPenalties())
                this.view?.setLastMatchPenalties(match.homePenaltyScore, match.awayPenaltyScore)

            if(match.match_facts != null){
                Log.d("MFPresenter", match.match_facts.toString())

                val events = match.match_facts.facts.events
                if(events != null){
                    for(event in events.reversed()){
                        view?.addMatchEvent(event)
                    }
                }

                val stats = match.match_facts.matchStats
                if(stats != null){
                    this.view?.showLastMatchStats(stats.localteam, stats.visitorteam)
                }
            }else this.view?.showLastMatchFactsNotFound()


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
        this.model.unsubscribeFromFirebase()
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as MatchFactsContract.MatchFactsView
        loadLastMatchInfo()

    }



}