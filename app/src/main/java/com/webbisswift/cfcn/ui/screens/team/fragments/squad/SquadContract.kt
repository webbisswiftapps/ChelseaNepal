package com.webbisswift.cfcn.ui.screens.team.fragments.squad

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.SquadPlayer

/**
 * Created by biswas on 21/12/2017.
 */

interface SquadContract{

    interface  SquadView:BaseView{
        fun showSquadLoading()
        fun addCoach(coach:String)
        fun addSquadGroup(title:String, players:List<SquadPlayer>)
        fun hideSquadLoading()
        fun clearList()
        fun showNoSquadError()
    }

    interface  SquadPresenter:BasePresenter{
        fun loadCurrentSquad()
    }

}