package com.webbisswift.cfcn.ui.screens.modal.compdetails.overview

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
import kotlinx.android.synthetic.main.fragment_competition_overview.*
import kotlinx.android.synthetic.main.layout_league_table_card.*


/**
 * Created by apple on 2/12/18.
 */

class CompetitionOverviewFragment : BaseFragment(), CompOverviewContract.CompOverviewView{

    var presenter: CompOverviewContract.CompOverviewPresenter?= null

    var leagueId:Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_competition_overview, null, true)
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

        fixturesRound.text = match.roundDesc
        fixturesHomeTeam.text = match.localTeam.data.name
        fixturesAwayTeam.text = match.visitorTeam.data.name
        fixturesDate.text = Utilities.getLocaleFormattedDate(match.time.starting_at.startDateTime)

        Glide.with(context).load(match.localTeam.data.logo_path).into(homeTeamLogoF)
        Glide.with(context).load(match.visitorTeam.data.logo_path).into(awayTeamLogoF)
        leagueNextMatchCard.visibility = View.VISIBLE

    }

    override fun hideUpcoming() {
        leagueNextMatchCard.visibility = View.GONE
    }

    override fun setLatestMatch(match: SMMatch) {
        resultsRound.text = match.roundDesc
        resultsHomeTeam.text = match.localTeam.data.name
        resultsAwayTeam.text = match.visitorTeam.data.name
        resultsDate.text = Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)

        awayScoreR.text = match.scores.visitorteam_score
        homeScoreR.text = match.scores.localteam_score


        resultsPenalties.text = match.statusDesc



        Glide.with(context).load(match.localTeam.data.logo_path).into(homeTeamLogoR)
        Glide.with(context).load(match.visitorTeam.data.logo_path).into(awayTeamLogoR)

        leagueLastMatchCard.visibility = View.VISIBLE

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
            val resource = if (isChelsea) R.layout.layout_table_item_chelsea else R.layout.layout_table_item
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
            val ltTitle = LayoutInflater.from(context).inflate(R.layout.layout_table_title, leagueTableHolder, false)
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