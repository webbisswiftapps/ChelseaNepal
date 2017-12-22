package com.webbisswift.cfcn.ui.screens.team.adapters

import com.webbisswift.cfcn.domain.model.InjuredPlayer
import com.webbisswift.cfcn.domain.model.SquadPlayer
import com.webbisswift.cfcn.domain.model.TransferOutPlayer
import com.webbisswift.cfcn.domain.model.TrasnferInPlayer

/**
 * Created by biswas on 21/12/2017.
 */

data class SquadItem(val type:Int,
                     val title:String?,
                     val player:SquadPlayer?,
                     val coach:String?,
                     val injuredPlayer: InjuredPlayer?,
                     val transferInPlayer:TrasnferInPlayer?,
                     val transferOutPlayer: TransferOutPlayer?
                     )

