package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.LatestFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.overview.SeasonPresenter
import com.webbisswift.cfcn.ui.screens.modal.compdetails.CompetitionDetailsUI
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.layout_form_guide_card.*
import kotlinx.android.synthetic.main.layout_league_table_card.*
import kotlinx.android.synthetic.main.layout_top_charts_card.*

/**
 * Created by apple on 12/14/17.
 */

class SeasonFragment : BaseFragment(), SeasonContract.SeasonView{

    var presenter: SeasonContract.SeasonPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_season, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loadAds()
    }

    private fun setListeners(){
        fullTeamStatsBtn.setOnClickListener {
            (parentFragment as LatestFragment)?.toCharts()
        }

        statsStandingsPLBtn.visibility = View.VISIBLE
        statsStandingsPLBtn.setOnClickListener{
            val i = CompetitionDetailsUI.getIntent(context!!, 8, "Premier League")
            startActivity(i)
        }
    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val model = SeasonModel(FirebaseDatabase.getInstance())
        this.presenter = SeasonPresenter(model)
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
        val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest)
    }


    /**
     * Season View Implementation
     */


    /**
     * Form Guide methods
     */

    override fun showFormGuideLoading() {
        formGuideCard?.visibility = View.VISIBLE
        if(formGuideSwitcher?.currentView?.id != R.id.formGuideProgress)
            formGuideSwitcher?.showPrevious()
    }

    override fun hideFormGuideLoading() {
        formGuideCard?.visibility = View.VISIBLE
        if(formGuideSwitcher?.currentView?.id == R.id.formGuideProgress)
            formGuideSwitcher?.showNext()
    }

    override fun addFormResult(type: String) {
        if(type.contentEquals("W")){
            val view = LayoutInflater.from(context).inflate(R.layout.win_tv, formHolder, false)
            formHolder?.addView(view)
        }else if(type.contentEquals("D")){
            val view = LayoutInflater.from(context).inflate(R.layout.draw_tv, formHolder, false)
            formHolder?.addView(view)
        }else if(type.contentEquals("L")){
            val view = LayoutInflater.from(context).inflate(R.layout.loss_tv, formHolder, false)
            formHolder?.addView(view)
        }
    }


    override fun clearFormPane() {
        formHolder?.removeAllViews()
    }

    override fun hideFormPane() {
        formGuideCard?.visibility = View.GONE
    }

    /**
     * Top Chart Methods
     */

    override fun showChartsLoading() {
        topScorersCard?.visibility = View.VISIBLE
        if(topScorersSwitcher?.currentView?.id != R.id.topScorersProgress)
            topScorersSwitcher?.showPrevious()
    }

    override fun hideChartsLoading() {
        topScorersCard?.visibility = View.VISIBLE
        if(topScorersSwitcher?.currentView?.id == R.id.topScorersProgress)
            topScorersSwitcher?.showNext()
    }

    override fun setTopScorer(player: SMSquad.SMSquadItem) {
        Glide.with(context).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(topScorerImg)
        topScorerName.text = player.player.data.common_name
        tsGoals.text = String.format("%d", player.goals)
    }

    override fun setTopAssister(player: SMSquad.SMSquadItem) {
        Glide.with(context).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(topAssistsImg)
        topAssistName.text = player.player.data.common_name
        tsAssists.text = String.format("%d", player.assists)
    }

    override fun setTopYC(player: SMSquad.SMSquadItem) {
        Glide.with(context).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(topYCImg)
        topYCPlayer.text = player.player.data.common_name
        tsYC.text = String.format("%d", player.yellowcards)
    }

    override fun setTopAppearance(player: SMSquad.SMSquadItem) {
        Glide.with(context).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(topAppImg)
        topAppPlayer.text = player.player.data.common_name
        tsApp.text = String.format("%d", player.appearences)
    }

    override fun setTopRC(player: SMSquad.SMSquadItem) {
        Glide.with(context).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(topRCImg)
        topRCPlayer.text = player.player.data.common_name
        tsRC.text = String.format("%d", player.redcards)
    }

    override fun hideCharts() {
        topScorersCard?.visibility = View.GONE
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