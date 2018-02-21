package com.webbisswift.cfcn.ui.screens.modal.compdetails.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMTeamLeagueStats
import kotlinx.android.synthetic.main.fragment_competition_stats.*
import kotlinx.android.synthetic.main.layout_stat_cs_fts.*
import kotlinx.android.synthetic.main.layout_stat_games.*
import kotlinx.android.synthetic.main.layout_stat_goals_conc.*
import kotlinx.android.synthetic.main.layout_stat_goals_cs.*

/**
 * Created by apple on 2/12/18.
 */


class LeagueStatsFragment : BaseFragment(), LeagueStatsContract.View {

    var presenter: LeagueStatsContract.Presenter? = null

    var leagueId: Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_competition_stats, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loadAds()
    }

    private fun setListeners() {

    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        if (leagueId != null && leagueId!! > 0) {
            val model = LeagueStatsModel(FirebaseDatabase.getInstance(), leagueId!!)
            this.presenter = LeagueStatsPresenter(model)
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


    private fun loadAds() {
        /*val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest) */
    }


    /**
     * Competition Overview View Implementation
     */


    override fun showLoading() {
        if(compStatsSwitcher?.currentView?.id != R.id.compStatsProgress)
                compStatsSwitcher?.showNext()
    }

    override fun hideLoading() {
        if(compStatsSwitcher?.currentView?.id == R.id.compStatsProgress)
            compStatsSwitcher?.showPrevious()
    }

    override fun setStatistics(stats: SMTeamLeagueStats) {
        wonCount.text = String.format("%d",stats.win.total)
        wonHAStat.text = String.format("H %d / A %d", stats.win.home, stats.win.away)

        drawCount.text = String.format("%d",stats.draw.total)
        drawHAStat.text = String.format("H %d / A %d", stats.draw.home, stats.draw.away)

        lossCount.text = String.format("%d",stats.lost.total)
        lossHAStat.text = String.format("H %d / A %d", stats.lost.home, stats.lost.away)

        totalCount.text = String.format("%d",stats.win.total + stats.lost.total + stats.draw.total)
        totalHAStat.text = String.format("H %d / A %d", stats.win.home + stats.lost.home + stats.draw.home, stats.win.away + stats.lost.away + stats.draw.away)


        goalsFor.text = String.format("%d", stats.goals_for.total)
        goalsForHAT.text = String.format("H %d / A %d", stats.goals_for.home, stats.goals_for.away)

        goalsScoredPerGame.text = String.format("%.2f", stats.avg_goals_per_game_scored.total)
        goalsScoredPerGameHAT.text = String.format("H %.2f / A %.2f", stats.avg_goals_per_game_scored.home, stats.avg_goals_per_game_scored.away)


        goalsAgainst.text = String.format("%d", stats.goals_against.total)
        goalsAgainstHAT.text = String.format("H %d / A %d", stats.goals_against.home, stats.goals_against.away)

        goalsConcededPerGame.text = String.format("%.2f", stats.avg_goals_per_game_conceded.total)
        goalsConcededPerGameHAT.text = String.format("H %.2f / A %.2f", stats.avg_goals_per_game_conceded.home, stats.avg_goals_per_game_conceded.away)

        cleanSheets.text = String.format("%d", stats.clean_sheet.total)
        cleanSheetsHAT.text = String.format("H %d / A %d", stats.clean_sheet.home, stats.clean_sheet.away)

        fts.text = String.format("%d", stats.failed_to_score.total)
        ftsHAT.text = String.format("H %d / A %d", stats.failed_to_score.home, stats.failed_to_score.away)

    }

    override fun showNoStatsError() {
        showLoading()
        compStatsProgress.visibility = View.GONE
    }
}