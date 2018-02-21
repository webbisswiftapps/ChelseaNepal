package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMSidelined

/**
 * Created by biswas on 22/12/2017.
 */


interface SidelinedContract{

    interface  SidelinedView: BaseView {
        fun showSidelinedLoading()
        fun hideSidelinedLoading()
        fun addPlayers(players:List<SMSidelined.SidelinedPlayer>)
        fun clearList()
        fun showNoSidelined()
    }

    interface  SidelinedPresenter: BasePresenter {
        fun loadSidelinedPlayers()
    }

    interface SidelinedModel{
        fun subscribeToSidelined(listener:ValueEventListener)
        fun unsubscribeFromSidelined()
    }

}