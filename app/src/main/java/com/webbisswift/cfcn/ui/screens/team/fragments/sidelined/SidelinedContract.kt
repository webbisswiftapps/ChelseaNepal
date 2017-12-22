package com.webbisswift.cfcn.ui.screens.team.fragments.sidelined

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.InjuredPlayer
import com.webbisswift.cfcn.domain.model.SquadPlayer

/**
 * Created by biswas on 22/12/2017.
 */


interface SidelinedContract{

    interface  SidelinedView: BaseView {
        fun showSidelinedLoading()
        fun hideSidelinedLoading()
        fun addPlayers(players:List<InjuredPlayer>)
        fun clearList()
        fun showNoSidelined()
    }

    interface  SidelinedPresenter: BasePresenter {
        fun loadSidelinedPlayers()
    }

}