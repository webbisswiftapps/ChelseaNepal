package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMStats
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterUI
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.v3_layout_match_stats_fragment.*


/**
 * Created by apple on 2/3/18.
 */

class MCStatsFragment: BaseFragment(),  MCStatsContract.MCStatsView{

    var presenter: MCStatsContract.MCStatsPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_layout_match_stats_fragment, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAds()
    }



    override fun initView() {
        val ep  = (activity as MatchCenterUI).endpoint!!
        val model = MatchCenterModel(ep, FirebaseDatabase.getInstance(), context!!)
        this.presenter = MCStatsPresenter(model)
        loadAds()
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun loadAds(){
         val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
    }


    /**
     *  Match Facts Display Methods
     *  */


    override fun showMatchNotStarted() {
        if(statsMainSwitcher?.currentView?.id == R.id.statsMain) statsMainSwitcher.showPrevious()
        statsErr?.text =  "Match not started yet."
    }

    override fun showMatchStatsUnavailable() {
        if(statsMainSwitcher?.currentView?.id == R.id.statsMain) statsMainSwitcher.showPrevious()
        statsErr?.text = "Match Stats unavailable for this match."
    }

    override fun setMatchStats(home: SMStats.StatsData, away: SMStats.StatsData) {
        if(statsMainSwitcher.currentView.id != R.id.statsMain) statsMainSwitcher.showNext()
        homePossesionVal.text = String.format("%d%%", home.possessiontime)
        awayPosessionVal.text = String.format("%d%%", away.possessiontime)
        homePossesion.progress = home.possessiontime.toFloat()
        awayPosession.progress = away.possessiontime.toFloat()

        val offsidesTotal = home.offsides + away.offsides
        val offsidesPerHome = home.offsides.toFloat() / offsidesTotal.toFloat()
        val offsidesPerAway = away.offsides.toFloat() / offsidesTotal.toFloat()
        homeOffsideVal.text = String.format("%d", home.offsides)
        awayOffSideVal.text = String.format("%d", away.offsides)
        homeOffsides.progress = offsidesPerHome * 100
        awayOffsides.progress = offsidesPerAway * 100


        val cornersTotal = home.corners + away.corners
        val cornersPerHome = home.corners.toFloat() / cornersTotal.toFloat()
        val cornersPerAway = away.corners.toFloat() / cornersTotal.toFloat()
        homeCornersVal.text = String.format("%d", home.corners)
        awayCornersVal.text = String.format("%d", away.corners)
        homeCorners.progress = cornersPerHome * 100
        awayCorners.progress = cornersPerAway * 100

        val savesTotal = home.saves + away.saves
        val savesPerHome = home.saves.toFloat() / savesTotal.toFloat()
        val savesPerAway = away.saves.toFloat() / savesTotal.toFloat()
        homeSavesVal.text = String.format("%d", home.saves)
        awaySavesVal.text = String.format("%d", away.saves)
        homeSaves.progress = savesPerHome * 100
        awaySaves.progress = savesPerAway * 100


        val foulsTotal = home.fouls + away.fouls
        val foulsPerHome = home.fouls.toFloat() / foulsTotal.toFloat()
        val foulsPerAway = away.fouls.toFloat() / foulsTotal.toFloat()
        homeFoulsVal.text = String.format("%d", home.fouls)
        awayFoulsVal.text = String.format("%d", away.fouls)
        homeFouls.progress = foulsPerHome * 100
        awayFouls.progress = foulsPerAway * 100

        val ycTotal = home.yellowcards + away.yellowcards
        val ycPerHome = home.yellowcards.toFloat() / ycTotal.toFloat()
        val ycPerAway = away.yellowcards.toFloat() / ycTotal.toFloat()
        homeYellowCardsVal.text = String.format("%d", home.yellowcards)
        awayYellowCardsVal.text = String.format("%d", away.yellowcards)
        homeYellowCards.progress = ycPerHome * 100
        awayYellowCards.progress = ycPerAway * 100


        val rcTotal = home.redcards + away.redcards
        val rcPerHome = home.redcards.toFloat() / rcTotal.toFloat()
        val rcPerAway = away.redcards.toFloat() / rcTotal.toFloat()
        homeRedCardsVal.text = String.format("%d", home.redcards)
        awayRedCardsVal.text = String.format("%d", away.redcards)
        homeRedCards.progress = rcPerHome * 100
        awayRedCards.progress = rcPerAway * 100

        homePassingVal.text = String.format("%d%%", home.passes.percentage)
        awayPassingVal.text = String.format("%d%%", away.passes.percentage)
        homePassing.progress = home.passes.percentage.toFloat()
        awayPassing.progress = away.passes.percentage.toFloat()


        if(home.attacks != null) {
            val tA = home.attacks.attacks + away.attacks.attacks
            val tAHP = home.attacks.attacks.toFloat() / tA.toFloat()
            val tAAP = away.attacks.attacks.toFloat() / tA.toFloat()
            homeTotalAttacksVal.text = String.format("%d", home.attacks.attacks)
            awayTotalAttacksVal.text = String.format("%d", away.attacks.attacks)
            homeTotalAttacks.progress = tAHP * 100
            awayTotalAttacks.progress = tAAP * 100


            val dA = home.attacks.dangerous_attacks + away.attacks.dangerous_attacks
            val dAHP = home.attacks.dangerous_attacks.toFloat() / dA.toFloat()
            val dAAP = away.attacks.dangerous_attacks.toFloat() / dA.toFloat()
            homeDangerousAttacksVal.text = String.format("%d", home.attacks.dangerous_attacks)
            awayDangerousAttacksVal.text = String.format("%d", away.attacks.dangerous_attacks)
            homeDangerousAttacks.progress = dAHP * 100
            awayDangerousAttacks.progress = dAAP * 100

        }

        if (home.shots != null) {

            val sA = home.shots.total + away.shots.total
            val sAHP = home.shots.total.toFloat() / sA.toFloat()
            val sAAP = away.shots.total.toFloat() / sA.toFloat()
            homeTotalShotsVal.text = String.format("%d", home.shots.total)
            awayTotalShotsVal.text = String.format("%d", away.shots.total)
            homeTotalAttacks.progress = sAHP * 100
            awayTotalAttacks.progress = sAAP * 100


            val ogA = home.shots.ongoal + away.shots.ongoal
            val ogAHP = home.shots.ongoal.toFloat() / ogA.toFloat()
            val ogAAP = away.shots.ongoal.toFloat() / ogA.toFloat()
            homeSOGVal.text = String.format("%d", home.shots.ongoal)
            awaySOGVal.text = String.format("%d", away.shots.ongoal)
            homeSOG.progress = ogAHP * 100
            awaySOG.progress = ogAAP * 100
        }

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