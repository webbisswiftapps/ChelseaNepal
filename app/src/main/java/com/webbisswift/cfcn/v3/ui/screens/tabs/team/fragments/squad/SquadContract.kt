package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMCoach
import com.webbisswift.cfcn.domain.model.v2.SMSquad

/**
 * Created by biswas on 21/12/2017.
 */

interface SquadContract{

    interface  SquadView:BaseView{
        fun showSquadLoading()
        fun addCoach(coach:SMCoach)
        fun addSquadGroup(title:String, players:List<SMSquad.SMSquadItem>)
        fun hideSquadLoading()
        fun clearList()
        fun showNoSquadError()
    }

    interface  SquadPresenter:BasePresenter{
        fun loadCurrentSquad()
        fun loadCoach()
    }

    interface SquadModel{
        fun subscribeToCoach(listener:ValueEventListener)
        fun subscribeToSquad(listener: ValueEventListener)
        fun unsubscribe()
    }

}