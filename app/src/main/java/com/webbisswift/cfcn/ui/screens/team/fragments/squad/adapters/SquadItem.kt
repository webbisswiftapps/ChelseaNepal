package com.webbisswift.cfcn.ui.screens.team.fragments.squad.adapters

import android.support.v7.widget.DialogTitle
import com.webbisswift.cfcn.domain.model.SquadPlayer

/**
 * Created by biswas on 21/12/2017.
 */

data class SquadItem(val type:Int,
                     val title:String?,
                     val player:SquadPlayer?,
                     val coach:String?
                     )
