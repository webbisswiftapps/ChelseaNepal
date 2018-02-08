package com.webbisswift.cfcn.ui.screens.match_center.fragments.lineups

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Lineup
import com.webbisswift.cfcn.domain.model.LineupPlayer
import com.webbisswift.cfcn.domain.model.MatchComment

/**
 * Created by biswas on 01/01/2018.
 */

interface MCLineupContract {

    interface MCLineupView : BaseView {
        fun setLineupChelsea(home:List<LineupPlayer>, subs:List<LineupPlayer>, formation:String)
        fun setLineupOpponent(away:List<LineupPlayer>, subs:List<LineupPlayer>, name:String, formation: String)

        fun showLineupError(error:String)
        fun showLineupsMain()

    }

    interface  MCLineupPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}