package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.utils.Utilities
import kotlinx.android.synthetic.main.v3_fragment_competition_overview.*
import kotlinx.android.synthetic.main.v3_layout_league_table_card.*
import kotlinx.android.synthetic.main.v3_score_small.*
import kotlinx.android.synthetic.main.v3_vs_small.*


/**
 * Created by apple on 2/12/18.
 */

class CompetitionOverviewFragment : BaseFragment(), CompOverviewContract.CompOverviewView{

    var presenter: CompOverviewContract.CompOverviewPresenter?= null

    var leagueId:Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_fragment_competition_overview, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loadAds()
    }

    private fun setListeners(){

    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        leagueId = arguments?.getInt("league")
        if(leagueId!= null && leagueId!! > 0) {
            val model = CompOverviewModel(FirebaseDatabase.getInstance(), leagueId!!)
            this.presenter = CompOverviewPresenter(model)
        }
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    private fun loadAds(){
        /*val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest) */
    }


    /**
     * Competition Overview View Implementation
     */


    /**
     * Matches Methods
     */

    override fun setUpcomingMatch(match: SMMatch) {
        leagueNextMatchCard.visibility = View.VISIBLE
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

    override fun hideUpcoming() {
        leagueNextMatchCard.visibility = View.GONE
    }

    override fun setLatestMatch(match: SMMatch) {
        leagueLastMatchCard.visibility = View.VISIBLE
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

    override fun hideLatest() {
        leagueLastMatchCard.visibility = View.GONE
    }


    /**
     *  League Table Methods
     */

    override fun showTableLoading() {
        leagueTableCard?.visibility = View.VISIBLE
        if(leageTableSwitcher?.currentView?.id != R.id.leagueTableProgress)
            leageTableSwitcher?.showPrevious()
    }

    override fun hideTableLoading() {
        leagueTableCard?.visibility = View.VISIBLE
        if(leageTableSwitcher?.currentView?.id == R.id.leagueTableProgress)
            leageTableSwitcher?.showNext()
    }

    override fun setTableTitle(title:String) {
        tableTitle.text = title
    }

    override fun addTeamToTable(team: SMStandingItem, isChelsea:Boolean) {
        if(context != null) {
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
            val guide = team.recent_form.toCharArray()
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
    }

    override fun clearTable() {
        if(context != null) {
            leagueTableHolder?.removeAllViews()
            val ltTitle = LayoutInflater.from(context).inflate(R.layout.v3_layout_table_title, leagueTableHolder, false)
            leagueTableHolder?.addView(ltTitle)
        }
    }

    override fun hideTableCard() {
        leagueTableCard?.visibility = View.GONE
    }

    override fun showTableCard() {
        leagueTableCard?.visibility = View.VISIBLE
    }



}