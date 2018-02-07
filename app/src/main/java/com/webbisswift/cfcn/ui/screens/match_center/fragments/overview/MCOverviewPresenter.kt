package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.domain.model.v2.SMMatch
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
                    val lmInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
                    presentNextMatchInfo(lmInfo)
                } catch (e: Exception) {
                    e.printStackTrace()
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

            if(match.tv_guide_all != null) {
                var tv = match.tv_guide_all[model.getUserCountry()];
                if (tv == null || tv.isBlank()) {
                    tv = match.tv_guide_all["International"]
                }

                if (tv != null && tv.isNotBlank()) {
                    this.view?.setTvGuide(tv)
                } else this.view?.setTvGuide("Not Available.")
            }else this.view?.setTvGuide("Not Available.")


            if(match.weather != null){
                val weather = match.weather
                view?.setWeather(weather.conditionDesc, weather.temperatureDesc, weather.icon)
            }else view?.hideWeather()


            if(match.headtohead != null){
                view?.setHeadToHead(match.headtohead)
            }else view?.hideHeadToHead()


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