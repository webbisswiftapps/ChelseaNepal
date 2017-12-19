package com.webbisswift.cfcn.ui.screens.home.fragments.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.LeagueTableItem
import com.webbisswift.cfcn.domain.model.TopScorer
import com.webbisswift.cfcn.ui.screens.home.fragments.overview.SeasonPresenter
import kotlinx.android.synthetic.main.layout_form_guide_card.*
import kotlinx.android.synthetic.main.layout_league_table_card.*
import kotlinx.android.synthetic.main.layout_top_scorers_card.*

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

    private fun setListeners(){

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
     * Top Scorer Methods
     */

    override fun showTopScorersLoading() {
        topScorersCard?.visibility = View.VISIBLE
        if(topScorersSwitcher?.currentView?.id != R.id.topScorersProgress)
            topScorersSwitcher?.showPrevious()    }

    override fun hideTopScorersLoading() {
        topScorersCard?.visibility = View.VISIBLE
        if(topScorersSwitcher?.currentView?.id == R.id.topScorersProgress)
            topScorersSwitcher.showNext()
    }

    override fun addTopSocrer(scorer: TopScorer) {
        if(context != null) {
            val topScorerItem = LayoutInflater.from(context).inflate(R.layout.layout_top_scorer_item,
                    topScorersHolder, false)

            topScorerItem.findViewById<TextView>(R.id.topScorerName).text = scorer.player
            topScorerItem.findViewById<TextView>(R.id.topScorerGoals).text = scorer.goals

            topScorersHolder?.addView(topScorerItem)
        }
    }

    override fun clearTopScorerPane() {
        if(context !=null) {
            topScorersHolder?.removeAllViews()

            val titleView = LayoutInflater.from(context).inflate(R.layout.layout_top_scorer_title, topScorersHolder, false)
            topScorersHolder?.addView(titleView)
        }
    }

    override fun hideTopScorerPane() {
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

    override fun addTeamToTable(team: LeagueTableItem, isChelsea:Boolean) {
        if(context != null) {
            val resource = if (isChelsea) R.layout.layout_table_item_chelsea else R.layout.layout_table_item
            val ltItem = LayoutInflater.from(context).inflate(resource,
                    leagueTableHolder, false)

            ltItem.findViewById<TextView>(R.id.position).text = team.position
            ltItem.findViewById<TextView>(R.id.teamName).text = team.team
            ltItem.findViewById<TextView>(R.id.gamesPlayed).text = team.matchesPlayed
            ltItem.findViewById<TextView>(R.id.gamesWon).text = team.wins
            ltItem.findViewById<TextView>(R.id.gamesDrawn).text = team.draws
            ltItem.findViewById<TextView>(R.id.gamesLost).text = team.losses
            ltItem.findViewById<TextView>(R.id.goalStats).text = team.goalsFor + ":" + team.goalsAgainst
            ltItem.findViewById<TextView>(R.id.goalDifference).text = team.goalDifference
            ltItem.findViewById<TextView>(R.id.points).text = team.getPts()

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