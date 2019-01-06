package com.webbisswift.cfcn.v3.ui.screens.tabs.latest.fragments.competitions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.*
import com.webbisswift.cfcn.utils.Utilities
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.CompetitionDetailsUI
import kotlinx.android.synthetic.main.v3_comp_card.*
import kotlinx.android.synthetic.main.v3_score_small.*
import kotlinx.android.synthetic.main.v3_vs_small.*

class CompFragment : Fragment(){




    lateinit var mViewModel: CompViewModel
     var isCup:Boolean = false
     var leagueId :Int = -1

    /** ------------- Lifecycle Methods ---------------*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  =  inflater.inflate(R.layout.v3_comp_card, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(checkCupDetails()) {
            setListeners()
            mViewModel = ViewModelProviders.of(this).get(CompViewModel::class.java)
        }


    }


    override fun onResume() {
        super.onResume()
        mViewModel.getStandings(leagueId).observe(this, Observer { league -> updateStandings(league) })
        mViewModel.getMatches(leagueId).observe(this, Observer { matches -> updateMatches(matches) })
    }


    /** ------------- Lifecycle Methods END ---------------*/



    /** ------------- Custom Methods ---------------*/


    private fun setListeners(){}

    private fun checkCupDetails():Boolean{

        if(arguments != null) {
            isCup = arguments!!.getBoolean("is_cup")
            leagueId = arguments!!.getInt("league")
            val name = arguments!!.getString("comp_name", "COMPETITION")

            if(isCup) updateStandings(null)

            compCard.setOnClickListener {
                if(context != null) {
                    context!!.startActivity(CompetitionDetailsUI.getIntent(context!!, leagueId, name))
                }
            }

            return true
        }else return false
    }

    /** -------------  END ---------------*/


    /** ------------- UI Manipulation Methods ---------------*/


    private fun updateStandings(standing:List<SMStandingItem>?){

        if(standing != null) {
            leagueTableHolder.visibility = View.VISIBLE
            leagueTableTitle.visibility = View.VISIBLE

            clearTable()

            if(!standing[0].group_name.isNullOrEmpty()){
                leagueTableTitle.text = standing[0].group_name
            }else leagueTableTitle.text = "STANDINGS"

            var chelseaFound = false
            for (item in standing.take(3)) {
                val isChelsea = (item.team_id == SMConstants.CHELSEA_TEAM_ID)
                addItemToTable(item, isChelsea)
                if(isChelsea){ chelseaFound = true}
            }



            if(chelseaFound){
               addItemToTable(standing[3], false)
            }else{
                //find chelsea and place it as the sixth item
                val itemx = standing.filter { SMConstants.CHELSEA_TEAM_ID == it.team_id }
                addItemToTable(itemx[0], true)
            }
        }else{

            leagueTableHolder.visibility = View.GONE
            leagueTableTitle.visibility = View.GONE
        }
    }

    private fun addItemToTable(team:SMStandingItem, isChelsea:Boolean){
            val resource = if (isChelsea) R.layout.v3_layout_table_item_chelsea else R.layout.v3_layout_table_item
            val ltItem = LayoutInflater.from(context).inflate(resource,
                    leagueTableHolder, false)

            ltItem.findViewById<TextView>(R.id.position).text = String.format("%d",team.position)
            ltItem.findViewById<TextView>(R.id.teamName).text = SMTeamShort.getTeamShort(team.team_name)
            ltItem.findViewById<TextView>(R.id.gamesPlayed).text = String.format("%d",team.overall.games_played)
            ltItem.findViewById<TextView>(R.id.gamesWon).text = String.format("%d", team.overall.won)
            ltItem.findViewById<TextView>(R.id.gamesDrawn).text = String.format("%d", team.overall.draw)
            ltItem.findViewById<TextView>(R.id.gamesLost).text = String.format("%d", team.overall.lost)
            ltItem.findViewById<TextView>(R.id.goalStats).text = String.format("%d : %d", team.overall.goals_scored, team.overall.goals_against)
            ltItem.findViewById<TextView>(R.id.goalDifference).text = String.format("%s", team.total.goal_difference)
            ltItem.findViewById<TextView>(R.id.points).text = String.format("%d", team.total.points)

            val formHolder = ltItem.findViewById<LinearLayout>(R.id.formGuideHolder)
            val guide = team.recent_form.toCharArray().reversed()
            for(g in guide){
                var item = 0
                when(g){
                    'D'-> item = R.layout.draw_small_tv
                    'W'-> item = R.layout.win_small_tv
                    'L'-> item = R.layout.loss_small_tv
                }
                try{
                    val it = LayoutInflater.from(context).inflate(item, formHolder, false)
                    formHolder.addView(it)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            leagueTableHolder?.addView(ltItem)

    }


    private fun clearTable(){
            leagueTableHolder?.removeAllViews()
            val ltTitle = LayoutInflater.from(context).inflate(R.layout.v3_layout_table_title, leagueTableHolder, false)
            leagueTableHolder?.addView(ltTitle)
    }


    private fun updateMatches(matches:SMLeagueMatches?){

        if(matches != null){

            if(!isCup) {
                if (matches.next != null) {
                    upcomingHolder.visibility = View.VISIBLE
                    resultHolder.visibility = View.GONE
                    showUpcoming(matches.next)
                } else if (matches.last != null) {
                    upcomingHolder.visibility = View.GONE
                    resultHolder.visibility = View.VISIBLE
                    showLast(matches.last)
                } else {
                    upcomingHolder.visibility = View.GONE
                    resultHolder.visibility = View.GONE
                }
            }else{

                if (matches.next != null) {
                    upcomingHolder.visibility = View.VISIBLE
                    showUpcoming(matches.next)
                } else  upcomingHolder.visibility = View.GONE

                if (matches.last != null && leagueTableHolder.visibility == View.GONE) {
                    resultHolder.visibility = View.VISIBLE
                    showLast(matches.last)
                } else resultHolder.visibility = View.GONE


            }

        }else{
            upcomingHolder.visibility = View.GONE
            resultHolder.visibility = View.GONE
        }
    }


    private fun showUpcoming(match:SMMatch){
        vsStageRound.text = match.roundDesc
        //vsCompetition.text = match.competitionName
        vsCompetition.visibility = View.GONE
        vsHomeTeam.text = match.localTeam.data.minimalName
        vsAwayTeam.text = match.visitorTeam.data.minimalName
        vsDate.text = Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)
        vsTime.text = Utilities.getLocaleFormattedTimeOnly(match.time.starting_at.startDateTime)

        Glide.with(context!!).load(match.localTeam.data.logo_path).into(vsHomeTeamLogo)
        Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(vsAwayTeamLogo)
    }

    private fun showLast(match:SMMatch){
        lmCompetition.text = match.roundDesc
        lmHomeTeam.text = match.localTeam.data.minimalName
        lmAwayTeam.text = match.visitorTeam.data.minimalName
        lmDateTime.text = Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)

        lmHomeScore.text = match.scores.localteam_score
        lmAwayScore.text = match.scores.visitorteam_score



        if (match.time.showPenalties()) {

            lmPenScore.visibility = View.VISIBLE
            lmPenScore.text = match.scores.localteam_pen_score + " - " + match.scores.visitorteam_pen_score + " (pen) "
        } else lmPenScore.visibility = View.GONE


        if (match.aggregate != null) {
            lmAggScore.visibility = View.VISIBLE

            if (match.aggregate.data.localteam_id == match.localteam_id) {
                lmAggScore.text = match.aggregate.data.result + " (agg)"
            } else {
                lmAggScore.text = match.aggregate.data.result.reversed() + " (agg)"
            }

        } else lmAggScore.visibility = View.GONE

        lmStatus.text = match.statusDesc

        Glide.with(context!!).load(match.localTeam.data.logo_path).into(lmHomeTeamLogo)
        Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(lmAwayTeamLogo)

    }



    /** -------------  END ---------------*/

}