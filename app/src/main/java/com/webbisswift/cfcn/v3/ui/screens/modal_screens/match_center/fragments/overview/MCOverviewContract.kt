package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import com.webbisswift.cfcn.domain.model.v2.SMMatch


/**
 * Created by apple on 12/31/17.
 */


interface  MCOverviewContract{

    interface MCOverviewView : BaseView {


        fun setMatchEvents(events: List<SMEvents.EventData>, homeTeamId:Int)
        fun clearMatchEventsCard()
        fun showMatchEventsCard()
        fun hideMatchEventsCard()

        fun showMatchNotStarted()


        fun setWeather(condition:String, temperature:String, conditionURL:String)
        fun hideWeather()

        fun setMatchDate(date:String)
        fun hideMatchDate()
        fun setMatchTournament(tournament:String)
        fun hideMatchTournament()
        fun setMatchVenue(venue:String)
        fun hideMatchVenue()
        fun setReferee(referee:String)
        fun hideReferee()

        fun setHeadToHead(h2h: List<SMMatch>)
        fun hideHeadToHead()



    }

    interface  MCOverviewPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}