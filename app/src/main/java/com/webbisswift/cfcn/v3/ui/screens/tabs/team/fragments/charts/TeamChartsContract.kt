package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMSquad

/**
 * Created by apple on 2/10/18.
 */

interface TeamChartsContract{

    interface TeamChartsView : BaseView {

        fun setTeamSquad(squad:List<SMSquad.SMSquadItem>)
        fun setTeamSquadLoading()
        fun hideTeamSquadLoading()
        fun showSquadNotAvailable()
    }

    interface TeamChartsPresenter : BasePresenter{
        fun loadSquad()
    }
}