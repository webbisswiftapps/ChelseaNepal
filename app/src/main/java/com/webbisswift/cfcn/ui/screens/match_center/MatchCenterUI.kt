package com.webbisswift.cfcn.ui.screens.match_center

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseActivity
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.ui.screens.home.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.MCLiveTickerFragment
import com.webbisswift.cfcn.ui.screens.match_center.fragments.overview.MCOverviewFragment
import kotlinx.android.synthetic.main.activity_match_center.*


/**
 * Created by apple on 12/31/17.
 */


class MatchCenterUI : BaseActivity(), MatchCenterContract.MatchCenterView{

    var presenter: MatchCenterContract.MatchCenterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_center)
        initView()
    }





    override fun initView() {
        val model = MatchCenterModel(FirebaseDatabase.getInstance())
        this.presenter =MatchCenterPresenter(model)
        setupTabs()
        setListeners()
    }


    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(MCOverviewFragment(), R.layout.tab_overview)
        adapter.addFragment(Fragment(), R.layout.tab_lineups)
        adapter.addFragment(MCLiveTickerFragment(), R.layout.tab_ticker)

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)
    }

    private fun setListeners(){
        backButton.setOnClickListener {
            finish()
        }
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

    override fun setNextMatchDate(date: String) {
        nextMatchTimings?.text = date
    }

    override fun setNextMatchPenalties(homePenalties: String, awayPenalties: String) {
        nextMatchPenalties?.visibility = View.VISIBLE
        nextMatchPenalties?.text = "(Pens "+homePenalties+" - "+awayPenalties+")"
    }

    override fun setNextMatchScore(homeScore: String, awayScore: String) {
        homeScoreLR?.text = homeScore
        awayScoreLR?.text = awayScore
    }

    override fun setCurrentMatchStatus(status: String) {
        hifen?.text = status
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