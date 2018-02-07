package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import android.os.Bundle
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
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterUI
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.layout_head_to_head.*
import kotlinx.android.synthetic.main.layout_match_stats_card.*
import kotlinx.android.synthetic.main.layout_match_stats_events.*
import kotlinx.android.synthetic.main.layout_tv_card.*
import kotlinx.android.synthetic.main.layout_weather.*

/**
 * Created by apple on 12/31/17.
 */


class MCOverviewFragment : BaseFragment(), MCOverviewContract.MCOverviewView{

    var presenter: MCOverviewContract.MCOverviewPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_mc_overview, null, true)
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

/*
    override fun hideLastMatchFactsLoading() {
        //do nothing
    }

    override fun showLastMatchFactsNotFound() {
        Log.d("MatchFactsUI","Match facts not found..")
        Log.d("MCOverview","match has not started yet!")
        hideMatchEventsCard()
    }

    override fun addMatchEvent(event: FactsMatchEvent) {

        val nV = LayoutInflater.from(context).inflate(R.layout.layout_match_event_item, matchEventsHolder, false)

        val eventTypeIV = nV?.findViewById<ImageView>(R.id.eventTypeImg)
        val eventPlayerTV = nV?.findViewById<TextView>(R.id.eventPlayer)
        val eventTimeTV = nV?.findViewById<TextView>(R.id.eventTime)
        val eventExtraTV = nV?.findViewById<TextView>(R.id.eventExtra)


        eventPlayerTV?.text = event.player

        val eventTime = event.minute + (if(event.extra_min.isNotBlank()) "+"+event.extra_min+"'" else "'")
        eventTimeTV?.text = eventTime

        when(event.type){
            "goal"->{
                eventTypeIV?.setImageResource(R.drawable.goal)
                if(event.assist.isNotBlank()) eventExtraTV?.text = "Assist: "+event.assist else eventExtraTV?.visibility = View.INVISIBLE
            }

            "subst"->{
                eventTypeIV?.setImageResource(R.drawable.sub)
                eventExtraTV?.text = "Out: "+event.assist
            }

            "yellowcard"->{
                eventTypeIV?.setImageResource(R.drawable.yellow_card)
                eventExtraTV?.visibility = View.INVISIBLE
            }

            "redcard"->{
                eventTypeIV?.setImageResource(R.drawable.red_card)
                eventExtraTV?.visibility = View.INVISIBLE
            }
            else ->{
                eventTypeIV?.visibility = View.INVISIBLE
            }
        }

        matchEventsHolder?.addView(nV)

    }

    override fun showMatchEventsCard(){
        matchEventsCard?.visibility = View.VISIBLE
    }

    override fun hideMatchEventsCard() {
        matchEventsCard?.visibility = View.GONE
    }



    override fun showLastMatchStats(homeStats: MatchStat, awayStat: MatchStat) {
        homePossesion?.text = homeStats.possesiontime
        awayPosession?.text = awayStat.possesiontime

        homeSOT?.text = homeStats.shots_ongoal
        awaySOT?.text = awayStat.shots_ongoal

        homeShots?.text = homeStats.shots_total
        awayShots?.text = awayStat.shots_total

        homeOffsides?.text = homeStats.offsides
        awayOffsides?.text = awayStat.offsides

        homeYellow?.text = homeStats.yellowcards
        awayYellow?.text = awayStat.yellowcards

        homeRed?.text = homeStats.redcards
        awayRed?.text = awayStat.redcards
    }


    override fun showMatchNotStarted() {
        Log.d("MCOverview","match has not started yet!")
        hideMatchEventsCard()
    }*/

    override fun setTvGuide(guide: String) {
        tvGuideCard?.visibility = View.VISIBLE
        tvGuide?.text = guide
    }

    override fun hideTVGuide() {
        tvGuideCard?.visibility = View.GONE
    }

    override fun setWeather(c: String, t: String, url: String) {
        weatherCard?.visibility = View.VISIBLE
        condition?.text = c
        temperature?.text = t
        Glide.with(context).load(url).into(conditionImg)
    }

    override fun hideWeather() {
        weatherCard?.visibility = View.GONE
    }

    override fun setHeadToHead(h2h: List<SMMatch>) {
        h2hCard?.visibility = View.VISIBLE
        for(match in h2h){
            val nV = LayoutInflater.from(context).inflate(R.layout.layout_h2h_item, h2hHolder, false)

            val homeName = nV.findViewById<TextView>(R.id.resultsHomeTeam)
            val awayName = nV.findViewById<TextView>(R.id.resultsAwayTeam)
            val resultsDate = nV.findViewById<TextView>(R.id.resultsDate)
            val resultsPenalties = nV.findViewById<TextView>(R.id.resultsPenalties)
            val homeScore = nV.findViewById<TextView>(R.id.homeScoreR)
            val awayScore = nV.findViewById<TextView>(R.id.homeScoreR)

            val homeTeam = SMTeamShort.getTeamShort(match.localTeam.data.name)
            val awayTeam = SMTeamShort.getTeamShort(match.visitorTeam.data.name)

            homeName.text = homeTeam
            awayName.text = awayTeam
            homeScore.text = match.scores.localteam_score
            awayScore.text = match.scores.visitorteam_score

            resultsDate.text = match.time.starting_at.date

            if(match.time.showPenalties()){
                resultsPenalties.visibility = View.VISIBLE
                resultsPenalties.text = "   "+match.scores.localteam_pen_score+" : "+match.scores.visitorteam_pen_score +" PEN"
            }

            h2hHolder.addView(nV)
        }
    }


    override fun hideHeadToHead() {
        h2hCard?.visibility = View.GONE
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