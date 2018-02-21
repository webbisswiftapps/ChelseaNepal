package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.liveticker

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterModel
import com.webbisswift.cfcn.utils.Utilities
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

                doAsync {
                    try {
                        val lmInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
                        uiThread {
                            presentNextMatchInfo(lmInfo)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        uiThread {
                            view?.showTickerNotStarted("Live ticker unavailable.")
                        }
                    }
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

    fun presentNextMatchInfo(match: SMMatch?) {


        if (match != null) {

            val startDT = match.time.starting_at.startDateTime
            val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)

            if (timeDiff < 0 || match.time.isLive) {

                if(match.comments != null && !match.comments.data.isEmpty()) {
                    view?.setComments(match.comments.data.reversed())
                }else view?.showTickerNotStarted("Live ticker has not started.")

            } else view?.showTickerNotStarted("Match has not started yet.")

        } else view?.showTickerNotStarted("Live ticker not available.")
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