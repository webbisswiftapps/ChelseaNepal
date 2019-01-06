package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview

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
 * Created by apple on 12/31/17.
 */

class  MCOverviewPresenter(val model: MatchCenterModel): MCOverviewContract.MCOverviewPresenter {

    var view: MCOverviewContract.MCOverviewView? = null


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
            //val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)

               view?.setMatchDate(Utilities.getLocaleFormattedDateOnly(startDT))
               view?.setMatchTournament(match.competitionDesc)
               if(match.venue!= null) {
                   view?.setMatchVenue(match.venue.data.name)
               }

            if(match.weather_report != null){
                val weather = match.weather_report
                try {
                    view?.setWeather(weather.conditionDesc, weather.temperatureDesc, weather.icon)
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }else view?.hideWeather()

            if(match.referee != null){
                view?.setReferee(match.referee?.data!!.fullname)
            }else view?.hideReferee()


            if(match.headtohead != null){
                view?.setHeadToHead(match.headtohead)
            }else view?.hideHeadToHead()

            if(match.events != null){
                view?.showMatchEventsCard()
                view?.clearMatchEventsCard()
                view?.setMatchEvents(match.events.data.reversed(), match.localteam_id)
            }else view?.hideMatchEventsCard()


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
        this.view = view as MCOverviewContract.MCOverviewView
        loadNextMatchInfo()

    }


}