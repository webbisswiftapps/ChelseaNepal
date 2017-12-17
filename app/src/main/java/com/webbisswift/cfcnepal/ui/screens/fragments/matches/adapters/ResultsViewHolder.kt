package com.webbisswift.cfcnepal.ui.screens.fragments.matches.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcnepal.R
import com.webbisswift.cfcnepal.domain.model.Match
import kotlinx.android.synthetic.main.layout_last_match_card.*
import kotlinx.android.synthetic.main.layout_result_item.view.*


/**
 * Created by apple on 12/7/17.
 */

class ResultsViewHolder(v: View): BaseMatchViewHolder(v){


    private var view:View  = v

    val matchCompetitionV:TextView
    val matchDate:TextView
    val matchAwayTeamLogo: ImageView
    var matchAwayTeamName:TextView
    var matchHomeTeamLogo:ImageView
    var matchHomeTeamName:TextView
    var matchAwayTeamScore:TextView
    var matchHomeTeamScore:TextView
    var matchPenatiesScore:TextView

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

    }

    override fun setMatch(match: Match){

        if(match != null) {
            matchCompetitionV.text = match.competition
            matchDate.text = match.start_date

            matchAwayTeamName.text = match.away
            matchAwayTeamScore.text = match.awayScore
            matchHomeTeamName.text = match.home
            matchHomeTeamScore.text = match.homeScore

            if(match.hadPenalties()){
                matchPenatiesScore.visibility = View.VISIBLE
                matchPenatiesScore.text = "(Pens "+match.homePenaltyScore+" - "+match.awayPenaltyScore+")"
            }else matchPenatiesScore.visibility = View.GONE

            Glide.with(view.context).load(match.homeShirtURL).into(matchHomeTeamLogo)
            Glide.with(view.context).load(match.awayShirtURL).into(matchAwayTeamLogo)


        }

    }



}