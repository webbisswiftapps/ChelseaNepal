package com.webbisswift.cfcn.ui.screens.team.adapters

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R
import org.jetbrains.anko.find

/**
 * Created by biswas on 22/12/2017.
 */

class SquadInjuredPlayerVH(private val view: View):SquadViewHolder(view){

    val injuredPlayer: TextView
    val injuryDesc: TextView
    val startDate:TextView
    val endDate:TextView


    init{
        injuredPlayer = this.view.findViewById(R.id.injuredPlayer)
        injuryDesc = this.view.findViewById(R.id.injuryDescription)
        startDate = this.view.findViewById(R.id.outSince)
        endDate = this.view.findViewById(R.id.expectedReturn)
    }


    override fun setItem(item: SquadItem) {
        val injured = item.injuredPlayer

        injuredPlayer.text = injured?.name
        injuryDesc.text = injured?.description
        startDate.text = injured?.startdate
        endDate.text = injured?.enddate

    }
}