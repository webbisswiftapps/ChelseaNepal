package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.overview

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
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.Utilities
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.layout_head_to_head.*
import kotlinx.android.synthetic.main.layout_match_referee.*
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




    override fun addMatchEvent(event: SMEvents.EventData) {

        val nV = LayoutInflater.from(context).inflate(R.layout.layout_match_event_item, matchEventsHolder, false)

        val eventTypeIV = nV?.findViewById<ImageView>(R.id.eventTypeImg)
        val eventPlayerTV = nV?.findViewById<TextView>(R.id.eventPlayer)
        val eventTimeTV = nV?.findViewById<TextView>(R.id.eventTime)
        val eventExtraTV = nV?.findViewById<TextView>(R.id.eventExtra)




        if(event?.extra_minute != null && event?.extra_minute > 0)
            eventTimeTV?.text = String.format("%d+%d'", event.minute, event.extra_minute)
        else eventTimeTV?.text = String.format("%d'", event?.minute)

        when(event.type){
            "goal"->{
                eventTypeIV?.setImageResource(R.drawable.goal)
                eventPlayerTV?.text = String.format("GOAL! %s ",event.player_name)
                val assist = if(!event.related_player_name.isNullOrBlank()) "Assist: "+event.related_player_name else ""
                val extra = assist + " "+event.result
                eventExtraTV?.text = extra
            }

            "penalty"->{
                eventTypeIV?.setImageResource(R.drawable.goal)
                eventPlayerTV?.text = String.format("GOAL(Pen)! %s ",event.player_name)
                val extra = event.result
                eventExtraTV?.text = extra
            }

            "substitution"->{
                eventTypeIV?.setImageResource(R.drawable.sub)
                eventPlayerTV?.text = String.format("Substitution: %s ",event.player_name)
                eventExtraTV?.text = "comes in for  "+event.related_player_name
            }

            "yellowcard"->{
                eventTypeIV?.setImageResource(R.drawable.yellow_card)
                eventPlayerTV?.text = String.format("Yellow Card to %s ", event.player_name)
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                eventExtraTV?.text = extra
            }

            "redcard"->{
                eventTypeIV?.setImageResource(R.drawable.yellow_card)
                eventPlayerTV?.text = String.format("%s ", event.player_name+" sent off")
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                eventExtraTV?.text = extra
            }

            "yellowred"->{
                eventTypeIV?.setImageResource(R.drawable.red_card)
                eventPlayerTV?.text = String.format("%s ", event.player_name+" receives second yellow")
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                eventExtraTV?.text = extra
            }

            "own-goal"->{
                eventTypeIV?.setImageResource(R.drawable.goal)
                eventPlayerTV?.text = String.format("OWN GOAL! by %s ", event.player_name)
                val extra = event.result
                eventExtraTV?.text = extra
            }

            "missed_penalty"->{
                eventTypeIV?.setImageResource(R.drawable.ic_miss)
                eventPlayerTV?.text = String.format("%s ", event.player_name+" missed penalty")
                eventExtraTV?.text = ""
            }



            else ->{
                eventTypeIV?.visibility = View.INVISIBLE
            }
        }

        matchEventsHolder?.addView(nV)

    }

    override fun clearMatchEventsCard() {
        matchEventsHolder?.removeAllViews()
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
        Glide.with(context!!).load(url).into(conditionImg)
    }

    override fun hideWeather() {
        weatherCard?.visibility = View.GONE
    }

    override fun setHeadToHead(h2h: List<SMMatch>) {
        h2hCard?.visibility = View.VISIBLE
        h2hHolder?.removeAllViews()
        for(match in h2h){
            val nV = LayoutInflater.from(context).inflate(R.layout.layout_h2h_item, h2hHolder, false)

            val homeName = nV.findViewById<TextView>(R.id.resultsHomeTeam)
            val awayName = nV.findViewById<TextView>(R.id.resultsAwayTeam)
            val resultsDate = nV.findViewById<TextView>(R.id.resultsDate)
            val resultsPenalties = nV.findViewById<TextView>(R.id.resultsPenalties)
            val homeScore = nV.findViewById<TextView>(R.id.homeScoreR)
            val awayScore = nV.findViewById<TextView>(R.id.awayScoreR)
            val resultCompetition = nV.findViewById<TextView>(R.id.resultsCompetition)

            val homeTeam = SMTeamShort.getTeamShort(match.localTeam.data.name)
            val awayTeam = SMTeamShort.getTeamShort(match.visitorTeam.data.name)

            homeName.text = homeTeam
            awayName.text = awayTeam
            homeScore.text = match.scores.localteam_score
            awayScore.text = match.scores.visitorteam_score
            resultsDate.text = ", "+Utilities.getLocaleFormattedDateOnly(match.time.starting_at.startDateTime)
            resultCompetition.text = match.league.data.name

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

    override fun setReferee(referee: String) {
        refereeCard?.visibility = View.VISIBLE
        refereeName?.text = referee
    }

    override fun hideReferee() {
        refereeCard?.visibility = View.GONE
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