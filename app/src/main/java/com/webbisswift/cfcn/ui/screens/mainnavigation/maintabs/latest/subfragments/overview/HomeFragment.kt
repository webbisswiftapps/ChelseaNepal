package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.overview

import android.content.SharedPreferences
import android.os.Bundle
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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.LatestFragment
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_overview.*


/**
 * Created by apple on 12/4/17.
 */

class HomeFragment:BaseFragment(), HomeContract.HomeView, SharedPreferences.OnSharedPreferenceChangeListener {


    var presenter:HomePresenter? = null

    lateinit var settings:SettingsHelper
    lateinit var blinkAnimation: Animation

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
        settings = SettingsHelper(context!!)
        this.settings.registerCountryChangeListener(this)
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

        matchFactsBtn.setOnClickListener({
            val mA:LatestFragment = parentFragment as LatestFragment
            mA.toMatchCenter("last-match")
        })

        matchCenterBtn.setOnClickListener({
            val mA:LatestFragment = parentFragment as LatestFragment
            mA.toMatchCenter("next-match")
        })
    }


    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
        adViewLarge?.loadAd(adRequest)
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
        Glide.with(this).load(logo).into(homeTeamLogoLS)
    }

    override fun setNextMatchScoreAwayTeam(name: String, logo: String) {
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

    override fun setNextMatchPenalties(homePenalties: String, awayPenalties: String) {
        nextMatchPenalties?.visibility = View.VISIBLE
        nextMatchPenalties?.text = "   "+homePenalties+" : "+awayPenalties +" PEN"
    }

    override fun hideNextMatchPenalties() {
        nextMatchPenalties?.visibility = View.GONE
    }

    override fun setMatchStatus(status: String, blink:Boolean) {
        statusLS?.text = status

        if(blink){
            statusLS?.setBackgroundDrawable(resources.getDrawable(R.drawable.live_bg_round))
            statusLS?.startAnimation(blinkAnimation)
        }else{
            statusLS?.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_splash_gradient))
            statusLS?.clearAnimation()
        }

    }

    override fun setNextMatchVenue(venue: String) {
        nextMatchVenue?.text = venue
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

    override fun setLastMatchRound(round: String) {
        lastMatchRound?.text = round
    }

    override fun setLastMatchStatus(status: String) {
        lastMatchPenalties?.text = status
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




    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {

        if(key != null && key.contentEquals("user_country")) {
            this.presenter?.loadNextMatchInfo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.settings.unregisterCountryChangeListener(this)
    }


}