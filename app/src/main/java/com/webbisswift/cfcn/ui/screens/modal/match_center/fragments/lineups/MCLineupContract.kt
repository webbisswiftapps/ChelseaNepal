package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.lineups

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMLineup

/**
 * Created by biswas on 01/01/2018.
 */

interface MCLineupContract {

    interface MCLineupView : BaseView {
        fun setLineupChelsea(home:List<SMLineup.SMLineupPlayer>?, subs:List<SMLineup.SMLineupPlayer>?, formation:String?)
        fun setLineupOpponent(away:List<SMLineup.SMLineupPlayer>?, subs:List<SMLineup.SMLineupPlayer>?, name:String, formation: String?)

        fun showLineupError(error:String)
        fun showLineupsMain()

    }

    interface  MCLineupPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}