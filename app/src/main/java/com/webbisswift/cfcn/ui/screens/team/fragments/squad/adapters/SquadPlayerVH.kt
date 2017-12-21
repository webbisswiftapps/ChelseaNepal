package com.webbisswift.cfcn.ui.screens.team.fragments.squad.adapters

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 21/12/2017.
 */


class SquadPlayerVH(private val view: View):SquadViewHolder(view){

    val playerNumber: TextView
    val playerName:TextView

    init{
        playerNumber = this.view.findViewById(R.id.playerNumber)
        playerName = this.view.findViewById(R.id.playerName)
    }


    override fun setItem(item: SquadItem) {
        val player = item.player
        playerNumber.text = player?.number
        playerName.text = player?.name
    }
}