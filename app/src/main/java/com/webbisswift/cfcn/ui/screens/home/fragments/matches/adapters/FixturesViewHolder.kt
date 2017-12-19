package com.webbisswift.cfcn.ui.screens.home.fragments.matches.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/7/17.
 */
class FixturesViewHolder(v: View): BaseMatchViewHolder(v){


    private var view: View = v

    val matchCompetitionV: TextView
    val matchDate: TextView
    val matchAwayTeamLogo: ImageView
    var matchAwayTeamName: TextView
    var matchHomeTeamLogo: ImageView
    var matchHomeTeamName: TextView


    init{

        matchCompetitionV = this.view.findViewById<TextView>(R.id.fixturesCompetition)
        matchDate = this.view.findViewById<TextView>(R.id.fixturesDate)
        matchAwayTeamLogo = this.view.findViewById<ImageView>(R.id.awayTeamLogoF)
        matchHomeTeamLogo = this.view.findViewById<ImageView>(R.id.homeTeamLogoF)
        matchHomeTeamName = this.view.findViewById<TextView>(R.id.fixturesHomeTeam)
        matchAwayTeamName = this.view.findViewById<TextView>(R.id.fixturesAwayTeam)

    }

    override  fun setMatch(match: Match){

        if(match != null) {
            matchCompetitionV.text = match.competition
            matchDate.text = match.start_date +" "+match.start_time

            matchAwayTeamName.text = match.away
            matchHomeTeamName.text = match.home

            Glide.with(view.context).load(match.homeShirtURL).into(matchHomeTeamLogo)
            Glide.with(view.context).load(match.awayShirtURL).into(matchAwayTeamLogo)

        }

    }



}