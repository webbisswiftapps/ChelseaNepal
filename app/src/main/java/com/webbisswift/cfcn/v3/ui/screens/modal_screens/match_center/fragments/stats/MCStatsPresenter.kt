package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.stats

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.utils.Utilities
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by biswas on 08/02/2018.
 */


class MCStatsPresenter(val model: MatchCenterModel): MCStatsContract.MCStatsPresenter {

    var view: MCStatsContract.MCStatsView? = null


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/


    override fun loadNextMatchInfo() {
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doAsync {
                    try {
                        val lmInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
                        uiThread {
                            presentNextMatchInfo(lmInfo)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
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

    fun presentNextMatchInfo(match: SMMatch?) {


        if (match != null) {

            val startDT = match.time.starting_at.startDateTime
            val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)

            if (timeDiff < 0 || match.time.isLive) {

                if(match.stats != null && match.stats.data.size == 2) {
                    val stats1 = match.stats.data[0]
                    val stats2 = match.stats.data[1]
                    if (stats1 != null && stats2 != null) {
                        try {
                            if (stats1.team_id == match.localteam_id) {
                                this.view?.setMatchStats(stats1, stats2)
                            } else this.view?.setMatchStats(stats2, stats1)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            this.view?.showMatchStatsUnavailable()
                        }
                    } else this.view?.showMatchStatsUnavailable()
                }else this.view?.showMatchStatsUnavailable()
            }else this.view?.showMatchNotStarted()

        }
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
        this.view = view as MCStatsContract.MCStatsView
        loadNextMatchInfo()

    }


}