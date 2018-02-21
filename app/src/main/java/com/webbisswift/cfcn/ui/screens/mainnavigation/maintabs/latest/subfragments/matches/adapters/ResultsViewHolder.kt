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

class ResultsViewHolder(v: View): BaseMatchViewHolder(v){


    private var view:View  = v

    private val matchCompetitionV:TextView
    private val matchRoundV:TextView
    private val matchDate:TextView
    private val matchAwayTeamLogo: ImageView
    private var matchAwayTeamName:TextView
    private var matchHomeTeamLogo:ImageView
    private var matchHomeTeamName:TextView
    private var matchAwayTeamScore:TextView
    private var matchHomeTeamScore:TextView
    private var matchPenatiesScore:TextView

    init{

        matchCompetitionV = this.view.findViewById<TextView>(R.id.resultsCompetition)
        matchDate = this.view.findViewById(R.id.resultsDate)
        matchAwayTeamLogo = this.view.findViewById(R.id.awayTeamLogoR)
        matchHomeTeamLogo = this.view.findViewById(R.id.homeTeamLogoR)
        matchAwayTeamScore = this.view.findViewById(R.id.awayScoreR)
        matchHomeTeamScore = this.view.findViewById(R.id.homeScoreR)
        matchHomeTeamName = this.view.findViewById(R.id.resultsHomeTeam)
        matchAwayTeamName = this.view.findViewById(R.id.resultsAwayTeam)
        matchPenatiesScore = this.view.findViewById(R.id.resultsPenalties)
        matchRoundV = this.view.findViewById(R.id.resultsRound)

    }

    override fun setMatch(match: SMMatch?){

        if(match != null) {
            matchCompetitionV.text = match.league.data.name
            matchRoundV.text = match.roundDesc
            matchDate.text = Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)

            matchAwayTeamName.text = match.visitorTeam.data.name
            matchAwayTeamScore.text = match.scores.visitorteam_score
            matchHomeTeamName.text = match.localTeam.data.name
            matchHomeTeamScore.text = match.scores.localteam_score

            matchPenatiesScore.text = match.statusDesc

            Glide.with(view.context).load(match.localTeam.data.logo_path).into(matchHomeTeamLogo)
            Glide.with(view.context).load(match.visitorTeam.data.logo_path).into(matchAwayTeamLogo)

        }

    }



}