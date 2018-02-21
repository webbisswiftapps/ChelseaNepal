package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 21/12/2017.
 */

class SquadCoachVH(private val view: View):SquadViewHolder(view){

    val coach: TextView

    init{
        coach = this.view.findViewById(R.id.coach)
    }


    override fun setItem(item: SquadItem, metrics:String?) {
        coach.text = item.coach
    }
}