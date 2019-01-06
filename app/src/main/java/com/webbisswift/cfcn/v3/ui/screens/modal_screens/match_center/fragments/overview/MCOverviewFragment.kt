package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.Utilities
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview.timeline.EventTimelineAdapter
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.v3_layout_head_to_head.*
import kotlinx.android.synthetic.main.v3_layout_match_stats_events.*
import kotlinx.android.synthetic.main.v3_layout_weather.*
import kotlinx.android.synthetic.main.v3_match_center_match_facts_layout.*
import kotlinx.android.synthetic.main.v3_score_small.view.*

/**
 * Created by apple on 12/31/17.
 */


class MCOverviewFragment : BaseFragment(), MCOverviewContract.MCOverviewView{

    var presenter: MCOverviewContract.MCOverviewPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_match_center_match_facts_layout, null, true)
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
        this.presenter = MCOverviewPresenter(model)
        loadAds()
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
        adViewLarge?.loadAd(adRequest)
    }



    /**
     *  Match Facts Display Methods
     *  */




    override fun setMatchEvents(events: List<SMEvents.EventData>, homeTeamId:Int) {

        val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        eventRecycler.layoutManager = mLayoutManager
        val mAdapter = EventTimelineAdapter(events, String.format("$homeTeamId"))
        eventRecycler.adapter = mAdapter

    }

    override fun clearMatchEventsCard() {

    }

    override fun showMatchEventsCard(){
    }

    override fun hideMatchEventsCard() {
        matchEventsCard?.visibility = View.GONE
    }



    override fun showMatchNotStarted() {
        Log.d("MCOverview","match has not started yet!")
        hideMatchEventsCard()
    }



    override fun setWeather(c: String, t: String, url: String) {
        weatherCard?.visibility = View.VISIBLE
        condition?.text = c
        temperature?.text = t
        Glide.with(context!!).load(url).into(conditionImg)
    }

    override fun hideWeather() {
        weatherCard?.visibility = View.GONE
    }

    override fun setHeadToHead(h2h: List<SMMatch>) {
        h2hCard?.visibility = View.VISIBLE
        h2hHolder?.removeAllViews()
        for(match in h2h){
            val nV = LayoutInflater.from(context).inflate(R.layout.v3_layout_h2h_item, h2hHolder, false)

            nV.lmCompetition.visibility = View.VISIBLE
            nV.lmCompetition.text = match.competitionDesc
            nV.lmHomeTeam.text = match.localTeam.data.minimalName
            nV.lmAwayTeam.text = match.visitorTeam.data.minimalName
            nV.lmDateTime.text = Utilities.getLocaleFormattedDateWithYear(match.time.starting_at.startDateTime)

            nV.lmHomeScore.text = match.scores.localteam_score
            nV.lmAwayScore.text = match.scores.visitorteam_score



            if (match.time.showPenalties()) {

                nV.lmPenScore.visibility = View.VISIBLE
                nV.lmPenScore.text = match.scores.localteam_pen_score + " - " + match.scores.visitorteam_pen_score + " (pen) "
            } else nV.lmPenScore.visibility = View.GONE


            if (match.aggregate != null) {
                nV.lmAggScore.visibility = View.VISIBLE

                if (match.aggregate.data.localteam_id == match.localteam_id) {
                    nV.lmAggScore.text = match.aggregate.data.result + " (agg)"
                } else {
                    nV.lmAggScore.text = match.aggregate.data.result.reversed() + " (agg)"
                }

            } else nV.lmAggScore.visibility = View.GONE

            nV.lmStatus.text = match.statusDesc

            Glide.with(context!!).load(match.localTeam.data.logo_path).into(nV.lmHomeTeamLogo)
            Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(nV.lmAwayTeamLogo)

            h2hHolder.addView(nV)
        }
    }


    override fun hideHeadToHead() {
        h2hCard?.visibility = View.GONE
    }

    override fun setReferee(referee: String) {
        matchRefereeRow.visibility = View.VISIBLE
        refereeName?.text = referee
    }

    override fun hideReferee() {
        matchRefereeRow?.visibility = View.GONE
    }


    override fun setMatchDate(date: String) {
        matchDateRow.visibility = View.VISIBLE
        mfMatchDate?.text = date
    }

    override fun hideMatchDate() {
        matchDateRow.visibility = View.GONE
    }

    override fun setMatchTournament(tournament:String) {
        matchTournamentRow.visibility = View.VISIBLE
        tournamentName.text = tournament
    }

    override fun hideMatchTournament() {
       matchTournamentRow.visibility = View.GONE
    }

    override fun setMatchVenue(venue:String) {
        matchVenueRow.visibility = View.VISIBLE
        venueName.text = venue
    }

    override fun hideMatchVenue() {
        matchVenueRow.visibility = View.GONE
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