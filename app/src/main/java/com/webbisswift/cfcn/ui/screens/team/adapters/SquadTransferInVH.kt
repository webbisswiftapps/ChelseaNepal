package com.webbisswift.cfcn.ui.screens.team.adapters

import android.text.Html
import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 22/12/2017.
 */


class SquadTransferInVH(private val view: View):SquadViewHolder(view){

    val transferPlayer: TextView
    val transferType: TextView
    val sourceClub: TextView
    val transferDate: TextView


    init{
        transferPlayer = this.view.findViewById(R.id.transferPlayer)
        transferType = this.view.findViewById(R.id.transferSource)
        sourceClub = this.view.findViewById(R.id.source)
        transferDate = this.view.findViewById(R.id.transferDate)
    }


    override fun setItem(item: SquadItem) {
        val player = item.transferInPlayer
        val typeEncoded = Html.fromHtml(player?.type)

        transferPlayer.text = player?.name
        transferType.text = typeEncoded
        sourceClub.text = player?.from_team
        transferDate.text = player?.date

    }
}