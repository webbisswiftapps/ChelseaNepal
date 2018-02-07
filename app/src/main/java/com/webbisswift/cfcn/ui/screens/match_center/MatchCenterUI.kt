package com.webbisswift.cfcn.ui.screens.match_center

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseActivity
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.ui.screens.home.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.match_center.fragments.lineups.MCLineupFragment
import com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.MCLiveTickerFragment
import com.webbisswift.cfcn.ui.screens.match_center.fragments.overview.MCOverviewFragment
import kotlinx.android.synthetic.main.activity_match_center.*
import android.view.animation.AnimationUtils
import com.webbisswift.cfcn.ui.screens.match_center.fragments.stats.MCStatsFragment


/**
 * Created by apple on 12/31/17.
 */


class MatchCenterUI : BaseActivity(), MatchCenterContract.MatchCenterView{

    var presenter: MatchCenterContract.MatchCenterPresenter? = null

    lateinit var blinkAnimation: Animation

     var endpoint:String = "next-match"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_center)
        initView()
    }





    override fun initView() {
        val ep = intent.getStringExtra("ENDPOINT")
        if(ep != null && ep.isNotBlank()) endpoint = ep

        val model = MatchCenterModel(ep, FirebaseDatabase.getInstance(), this)
        this.presenter = MatchCenterPresenter(model)
        blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_tween)
        setupTabs()
        setListeners()

    }




    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(MCOverviewFragment(), R.layout.tab_overview)
        adapter.addFragment(MCStatsFragment(), R.layout.tab_stats)
        adapter.addFragment(MCLineupFragment(), R.layout.tab_lineups)
        adapter.addFragment(MCLiveTickerFragment(), R.layout.tab_ticker)

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

        Log.d("MatchCenterUI"," Action : "+intent.action)
        if(switchToLineups()) viewPager.setCurrentItem(1, false)
    }

    private fun setListeners(){
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun switchToLineups():Boolean{
        return (intent.action != null && intent.action.contentEquals("com.webbisswift.cfcn.actions.OPEN_MATCH_CENTER_LINEUPS"))
    }



    /**
     *  Match Facts Display Methods
     *  */


    override fun setNextMatchAwayTeam(name: String, logo: String) {
        nextMatchAwayTeam?.text = name
        Glide.with(this).load(logo).into(awayTeamLogoLR)
    }

    override fun setNextMatchHomeTeam(name: String, logo: String) {
        nextMatchHomeTeam?.text = name
        Glide.with(this).load(logo).into(homeTeamLogoLR)
    }

    override fun setNextMatchCompetitionName(name: String) {
        nextMatchCompetition?.text = name
    }

    override fun setNextMatchDate(date: String, blink:Boolean) {
        nextMatchTimings?.text = date

        if(blink){
            nextMatchTimings?.setBackgroundDrawable(resources.getDrawable(R.drawable.live_bg_round))
            nextMatchTimings?.startAnimation(blinkAnimation)
        }else{
            nextMatchTimings?.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_splash_gradient))
            nextMatchTimings?.clearAnimation()
        }


    }

    override fun setNextMatchVenue(venue: String) {
        nextMatchVenue?.text = venue
    }

    override fun setNextMatchPenalties(homePenalties: String, awayPenalties: String) {
        nextMatchPenalties?.visibility = View.VISIBLE
        nextMatchPenalties?.text = "   "+homePenalties+" : "+awayPenalties +" PEN"
    }

    override fun setNextMatchScore(homeScore: String, awayScore: String) {
        homeScoreLR?.text = homeScore
        awayScoreLR?.text = awayScore
    }




    override fun showNoDataAndFinish() {
        finish()
    }



    /**
     * Base View Methods
     */
    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}