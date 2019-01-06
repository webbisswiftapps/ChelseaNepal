package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.utils.Utilities

import kotlinx.android.synthetic.main.v3_vs_small.view.*

/**
 * Created by apple on 12/7/17.
 */
class FixturesViewHolder(val v: View, val listener:ItemClickInterface): BaseMatchViewHolder(v){




    init {
        v.setOnClickListener {
            listener.onClickItem(adapterPosition)
        }
    }


    override  fun setMatch(match: SMMatch?){

        if(match != null) {
            v.vsStageRound.text = match.roundDesc
            v.vsCompetition.text = match.competitionName
            v.vsCompetition.visibility = View.VISIBLE
            v.vsHomeTeam.text = match.localTeam.data.minimalName
            v.vsAwayTeam.text = match.visitorTeam.data.minimalName
            v.vsDate.text = Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)
            v.vsTime.text = Utilities.getLocaleFormattedTimeOnly(match.time.starting_at.startDateTime)

            Glide.with(v.context!!).load(match.localTeam.data.logo_path).into(v.vsHomeTeamLogo)
            Glide.with(v.context!!).load(match.visitorTeam.data.logo_path).into(v.vsAwayTeamLogo)

        }

    }



}