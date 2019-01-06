package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseActivity
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.lineups.MCLineupFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.liveticker.MCLiveTickerFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview.MCOverviewFragment
import kotlinx.android.synthetic.main.v3_match_center_activity_modal.*
import android.view.animation.AnimationUtils
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts.TeamChartsFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.SidelinedFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferInFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferOutFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.stats.MCStatsFragment
import kotlinx.android.synthetic.main.v3_goal_scorer_small.view.*
import kotlinx.android.synthetic.main.v3_score_view_tp.*


/**
 * Created by apple on 12/31/17.
 */


class MatchCenterUI : BaseActivity(), MatchCenterContract.MatchCenterView{

    var presenter: MatchCenterContract.MatchCenterPresenter? = null

    lateinit var blinkAnimation: Animation

     var endpoint:String = "next-match"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v3_match_center_activity_modal)
        initView()
    }





    override fun initView() {

        /* 1. Check if we are here from OPEN_MATCH_CENTER or OPEN_MATCH_CENTER_LINEUPS Action */

        if(isOpenedFromNotification() || switchToLineups())
            endpoint = "next-match"
        else {

            val ep = intent.getStringExtra("ENDPOINT")
            if (ep != null && ep.isNotBlank()) endpoint = ep
        }



        val model = MatchCenterModel(endpoint, FirebaseDatabase.getInstance(), this)
        this.presenter = MatchCenterPresenter(model)
        blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_tween)
        setListeners()

    }




    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun setListeners(){
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun switchToLineups():Boolean{
        return (intent.action != null && intent.action.contentEquals("OPEN_MATCH_CENTER_LINEUPS"))
    }

    private fun isOpenedFromNotification():Boolean{
        return (intent.action != null && intent.action.contentEquals("OPEN_MATCH_CENTER"))
    }






    /**
     *  Match Facts Display Methods
     *  */

    override fun prepareMatchCenterTabs(isStarted:Boolean) {
        val adapter = FragmentPagerItems.with(this)

        adapter.add( "Match Facts", MCOverviewFragment::class.java)
        adapter.add( "Lineups", MCLineupFragment::class.java)
        if(isStarted) {
            adapter.add( "Stats", MCStatsFragment::class.java)
            adapter.add( "Ticker", MCLiveTickerFragment::class.java)
        }
        val viewPagerAdapter = FragmentPagerItemAdapter(supportFragmentManager, adapter.create())

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = viewPagerAdapter
        tabs.setViewPager(viewPager)

        Log.d("MatchCenterUI"," Action : "+intent.action)
        if(switchToLineups()) viewPager.setCurrentItem(2, false)
    }


    override fun setNextMatchAwayTeam(name: String, logo: String) {
        lmAwayTeam?.text = name
        Glide.with(this).load(logo).into(lmAwayTeamLogo)
    }


    override fun setNextMatchHomeTeam(name: String, logo: String) {
        lmHomeTeam?.text = name
        Glide.with(this).load(logo).into(lmHomeTeamLogo)
    }

    override fun setNextMatchCompetitionName(name: String) {
        nextMatchCompetition?.text = name
    }

    override fun setNextMatchDate(date: String, blink:Boolean) {
        nextMatchTimings?.text = date

        if(blink){
            nextMatchTimings?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_live_bg))
            nextMatchTimings?.startAnimation(blinkAnimation)
        }else{
            nextMatchTimings?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_tag_bg))
            nextMatchTimings?.clearAnimation()
        }
    }

    override fun setNextMatchVenue(venue: String) {
        nextMatchVenue?.text = venue
    }

    override fun setNextMatchPenalties(homePenalties: String, awayPenalties: String) {
        lmPenScore?.visibility = View.VISIBLE
        lmPenScore?.text = "   "+homePenalties+" : "+awayPenalties +" (pen)"
    }

    override fun setNextMatchScore(homeScore: String, awayScore: String) {
        lmHomeScore?.text = homeScore
        lmAwayScore?.text = awayScore
    }


    override fun setNextMatchTVGuide(match:SMMatch) {
        val settings = SettingsHelper(this)
        if (match.tv_guide_all != null) {
            var tv = match.tv_guide_all[settings.getUserCurrentCountry()]
            if (tv == null || tv.isBlank()) {
                tv = match.tv_guide_all["International"]
            }

            if (tv != null && tv.isNotBlank()) {
                channels.text = tv
            } else tvGuideDisp.visibility = View.GONE
        } else tvGuideDisp.visibility = View.GONE
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