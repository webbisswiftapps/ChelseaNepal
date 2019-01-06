package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import com.webbisswift.cfcn.domain.model.v2.SMCoach
import com.webbisswift.cfcn.domain.model.v2.SMSidelined
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.domain.model.v2.SMTransferItem

/**
 * Created by biswas on 21/12/2017.
 */

data class SquadItem(val type:Int,
                     val title:String?,
                     val player:SMSquad.SMSquadItem?,
                     val coach:SMCoach?,
                     val injuredPlayer: SMSidelined.SidelinedPlayer?,
                     val transferInPlayer:SMTransferItem?,
                     val transferOutPlayer: SMTransferItem?,
                     val metric:String?
                     )

