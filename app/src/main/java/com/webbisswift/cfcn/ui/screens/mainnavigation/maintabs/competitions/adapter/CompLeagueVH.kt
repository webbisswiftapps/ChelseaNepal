package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.RVItemClickListener

/**
 * Created by apple on 2/12/18.
 */


class CompLeagueVH(v: View, onclick:RVItemClickListener): BaseCompetitionsViewHolder(v){

    private var view: View = v

    private val compImg: ImageView
    private val compName:TextView

    init{
        compImg = view.findViewById(R.id.compImg)
        compName = view.findViewById(R.id.compName)

        view.setOnClickListener { onclick.onClick(view, adapterPosition) }
    }


    override fun setCompetition(competition: SMPlayingLeague?) {
        if(competition != null){
            compName.text = competition.name
            if(competition.country.equals("England",true))
                compImg.setImageResource(R.drawable.ic_comp_eng)
            else if(competition.country.equals("Europe", true))
                compImg.setImageResource(R.drawable.ic_comp_europe)
            else compImg.setImageResource(R.drawable.ic_comp_other)
        }
    }
}