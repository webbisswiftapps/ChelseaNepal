package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.matches.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.utils.Utilities

/**
 * Created by apple on 12/7/17.
 */
class FixturesViewHolder(v: View): BaseMatchViewHolder(v){


    private var view: View = v

    private val matchCompetitionV: TextView
    private val matchDate: TextView
    private val matchAwayTeamLogo: ImageView
    private val matchAwayTeamName: TextView
    private val matchHomeTeamLogo: ImageView
    private val matchHomeTeamName: TextView
    private val matchRoundV : TextView

    init{

        matchCompetitionV = this.view.findViewById<TextView>(R.id.fixturesCompetition)
        matchDate = this.view.findViewById<TextView>(R.id.fixturesDate)
        matchAwayTeamLogo = this.view.findViewById<ImageView>(R.id.awayTeamLogoF)
        matchHomeTeamLogo = this.view.findViewById<ImageView>(R.id.homeTeamLogoF)
        matchHomeTeamName = this.view.findViewById<TextView>(R.id.fixturesHomeTeam)
        matchAwayTeamName = this.view.findViewById<TextView>(R.id.fixturesAwayTeam)
        matchRoundV = this.view.findViewById<TextView>(R.id.fixturesRound)

    }

    override  fun setMatch(match: SMMatch?){

        if(match != null) {
            matchCompetitionV.text = match.league.data.name
            matchRoundV.text = match.roundDesc

            var date = (Utilities.getLocaleFormattedDate(match.time.starting_at.startDateTime))
            if(match.time.status.contentEquals("POSTP")){
                date = date + " (Postponed)"
            }

            matchDate.text = date

            matchAwayTeamName.text = match.visitorTeam.data.name
            matchHomeTeamName.text = match.localTeam.data.name

            Glide.with(view.context).load(match.localTeam.data.logo_path).into(matchHomeTeamLogo)
            Glide.with(view.context).load(match.visitorTeam.data.logo_path).into(matchAwayTeamLogo)

        }

    }



}