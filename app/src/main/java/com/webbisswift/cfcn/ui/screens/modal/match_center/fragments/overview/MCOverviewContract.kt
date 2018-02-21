package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.overview

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import com.webbisswift.cfcn.domain.model.v2.SMMatch


/**
 * Created by apple on 12/31/17.
 */


interface  MCOverviewContract{

    interface MCOverviewView : BaseView {


        fun addMatchEvent(event: SMEvents.EventData)
        fun clearMatchEventsCard()
        fun showMatchEventsCard()
        fun hideMatchEventsCard()
        fun showMatchNotStarted()


        fun setTvGuide(guide:String)
        fun hideTVGuide()
        fun setReferee(referee:String)
        fun hideReferee()
        fun setWeather(condition:String, temperature:String, conditionURL:String)
        fun hideWeather()

        fun setHeadToHead(h2h: List<SMMatch>)
        fun hideHeadToHead()



    }

    interface  MCOverviewPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}