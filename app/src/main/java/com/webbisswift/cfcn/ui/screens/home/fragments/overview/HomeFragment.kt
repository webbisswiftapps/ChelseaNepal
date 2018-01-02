package com.webbisswift.cfcn.ui.screens.home.fragments.overview

import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import kotlinx.android.synthetic.main.layout_last_match_card.*
import kotlinx.android.synthetic.main.layout_next_match_card.*
import com.webbisswift.cfcn.domain.model.LeagueTableItem
import com.webbisswift.cfcn.domain.model.MatchEvent
import com.webbisswift.cfcn.ui.screens.home.MainActivity
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.layout_team_stats_epl_card.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_overview.*


/**
 * Created by apple on 12/4/17.
 */

class HomeFragment:BaseFragment(), HomeContract.HomeView{


    var presenter:HomePresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overview, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loadAds()
    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        val model = HomeModel(FirebaseDatabase.getInstance(),  context!!)
        this.presenter = HomePresenter(model)
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
        matchCountDown.setOnCountdownEndListener {
            countDownSwitcher.showNext()
        }

        viewTableBtn.setOnClickListener({
            val mA:MainActivity = activity as MainActivity
            mA.toSeasonTab()
        })

        matchFactsBtn.setOnClickListener({
            val mA:MainActivity = activity as MainActivity
            mA.toLastMatchFacts()
        })

        matchCenterBtn.setOnClickListener({
            val mA:MainActivity = activity as MainActivity
            mA.toMatchCenter()
        })
    }


    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest)
        adViewLarge.loadAd(adRequest)
    }

    /**
     * HomeView Implementation
     */


    /**
     *  Next Match Methods
     */
    override fun showNextMatchInfoLoading() {
        nextMatchCard?.visibility = View.VISIBLE
        if(nextMatchSwitcher?.currentView?.id != R.id.nextMatchProgress)
            nextMatchSwitcher?.showPrevious()
    }

    override fun hideNextMatchInfoLoading() {
        nextMatchCard?.visibility = View.VISIBLE
        if(nextMatchSwitcher?.currentView?.id == R.id.nextMatchProgress)
            nextMatchSwitcher.showNext()
    }

    override fun hideNextMatchCard() {
        nextMatchCard?.visibility = View.GONE
    }

    override fun setNextMatchAwayTeam(name: String, logo: String) {
        nextMatchAwayTeam?.text = name
        Glide.with(this).load(logo).into(awayTeamLogo)
    }

    override fun setNextMatchScoreHomeTeam(name: String, logo: String) {
        homeTeamLS?.text = name
        Glide.with(this).load(logo).into(homeTeamLogoLS)
    }

    override fun setNextMatchScoreAwayTeam(name: String, logo: String) {
        awayTeamLS?.text = name
        Glide.with(this).load(logo).into(awayTeamLogoLS)
    }

    override fun setCompetitionName(competition: String) {
        nextMatchCompetition?.text = competition
    }

    override fun setTVInfo(tvInfo: String) {
        nextmatchTVGuide?.text = tvInfo
    }

    override fun startNextMatchCountdown(seedTime: Long) {
        matchCountDown?.start(seedTime)
    }

    override fun setNextMatchTimings(timings: String) {
        nextMatchTimings?.text = timings
    }

    override fun switchToScores() {
        if(countDownSwitcher?.currentView?.id == R.id.nextMatchCountdownHolder){
            countDownSwitcher?.showNext()
        }
    }

    override fun switchToCountdown() {
        if(countDownSwitcher?.currentView?.id != R.id.nextMatchCountdownHolder){
            countDownSwitcher?.showPrevious()
        }
    }

    override fun setMatchHomeScore(score: String) {
        homeScoreLS?.text = score
    }

    override fun setMatchAwayScore(score: String) {
        awayScoreLS?.text = score
    }

    override fun setMatchStatus(status: String) {
        statusLS?.text = status
    }

    override fun addMatchEvent(event: MatchEvent) {
        //not done
    }

    /**
     * Last Match methods
     */

    override fun showLastMatchInfoLoading() {
        lastMatchCard?.visibility = View.VISIBLE
        if(lastMatchSwitcher?.currentView?.id != R.id.lastMatchProgress)
            lastMatchSwitcher?.showPrevious()
    }

    override fun setLastMatchAwayTeam(name: String, logo: String) {
        lastMatchAwayTeam?.text = name
        Glide.with(this).load(logo).into(awayTeamLogoLR)
    }

    override fun setLastMatchHomeTeam(name: String, logo: String) {
        lastMatchHomeTeam?.text = name
        Glide.with(this).load(logo).into(homeTeamLogoLR)
    }

    override fun setLastMatchCompetitionName(name: String) {
        lastMatchCompetition?.text = name
    }

    override fun setLastMatchDate(date: String) {
        lastMatchDate?.text = date
    }

    override fun setLastMatchPenalties(homePenalties: String, awayPenalties: String) {
        lastMatchPenalties?.visibility = View.VISIBLE
        lastMatchPenalties?.text = "(Pens "+homePenalties+" - "+awayPenalties+")"
    }

    override fun setLastMatchScore(homeScore: String, awayScore: String) {
        homeScoreLR?.text = homeScore
        awayScoreLR?.text = awayScore
    }

    override fun hideLastMatchInfoLoading() {
        lastMatchCard?.visibility = View.VISIBLE
        if(lastMatchSwitcher?.currentView?.id == R.id.lastMatchProgress)
            lastMatchSwitcher?.showNext()
    }

    override fun hideLastMatchCard() {
        lastMatchCard?.visibility =View.GONE
    }


    /**
     * Team Stats Methods
     */

    override fun showTeamStatsLoading() {
        teamStatsCard?.visibility = View.VISIBLE
        if(teamStatsSwitcher?.currentView?.id != R.id.teamStatsProgress)
            teamStatsSwitcher?.showPrevious()
    }

    override fun hideTeamStatsLoading() {
        teamStatsCard?.visibility = View.VISIBLE
        if(teamStatsSwitcher?.currentView?.id == R.id.teamStatsProgress)
            teamStatsSwitcher?.showNext()
    }

    override fun showTeamStats(item: LeagueTableItem) {

        tsCurrentPos?.text = item.position
        tsCurrentPoints?.text = item.getPts()
        tsWins?.text = item.wins
        tsDraws?.text = item.draws
        tsLosses?.text = item.losses
    }

    override fun hideTeamStatsPane() {
        teamStatsCard?.visibility = View.GONE
    }

}