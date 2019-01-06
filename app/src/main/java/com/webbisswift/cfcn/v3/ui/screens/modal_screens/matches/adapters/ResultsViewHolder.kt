package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.utils.Utilities
import kotlinx.android.synthetic.main.v3_score_small.*
import kotlinx.android.synthetic.main.v3_score_small.view.*


/**
 * Created by apple on 12/7/17.
 */

class ResultsViewHolder(val v: View, val listener:ItemClickInterface): BaseMatchViewHolder(v){


    init {
        v.setOnClickListener {
            listener.onClickItem(adapterPosition)
        }
    }



    override fun setMatch(match: SMMatch?){

        if(match != null) {
            v.lmCompetition.text = match.competitionDesc
            v.lmCompetition.visibility = View.VISIBLE
            v.lmHomeTeam.text = match.localTeam.data.minimalName
            v.lmAwayTeam.text = match.visitorTeam.data.minimalName
            v.lmDateTime.text = Utilities.getLocaleFormattedDate(match.time.starting_at.startDateTime)

            v.lmHomeScore.text = match.scores.localteam_score
            v.lmAwayScore.text = match.scores.visitorteam_score



            if (match.time.showPenalties()) {

                v.lmPenScore.visibility = View.VISIBLE
                v.lmPenScore.text = match.scores.localteam_pen_score + " - " + match.scores.visitorteam_pen_score + " (pen) "
            } else v.lmPenScore.visibility = View.GONE


            if (match.aggregate != null) {
                v.lmAggScore.visibility = View.VISIBLE

                if (match.aggregate.data.localteam_id == match.localteam_id) {
                    v.lmAggScore.text = match.aggregate.data.result + " (agg)"
                } else {
                    v.lmAggScore.text = match.aggregate.data.result.reversed() + " (agg)"
                }

            } else v.lmAggScore.visibility = View.GONE

            v.lmStatus.text = match.statusDesc

            Glide.with(v.context!!).load(match.localTeam.data.logo_path).into(v.lmHomeTeamLogo)
            Glide.with(v.context!!).load(match.visitorTeam.data.logo_path).into(v.lmAwayTeamLogo)


        }

    }



}