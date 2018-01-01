package com.webbisswift.cfcn.ui.screens.match_center.fragments.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import kotlinx.android.synthetic.main.ad_card_large_overview.*
import kotlinx.android.synthetic.main.ad_card_small_banner_season.*
import kotlinx.android.synthetic.main.layout_match_stats_card.*
import kotlinx.android.synthetic.main.layout_match_stats_events.*
import kotlinx.android.synthetic.main.layout_tv_card.*

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
        val model = MatchCenterModel(FirebaseDatabase.getInstance())
        this.presenter = MCOverviewPresenter(model)
        loadAds()
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .addTestDevice("D97506CE44741D62F39273476ECCCA35")
                .addTestDevice("C59EB2BE510BBC21EF6D8F6A3D585248")
                .build()
        adView?.loadAd(adRequest)
        adViewLarge?.loadAd(adRequest)
    }



    /**
     *  Match Facts Display Methods
     *  */



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

    override fun setTvGuide(guide: String) {
        tvGuide?.text = guide
    }

    override fun showMatchNotStarted() {
        Log.d("MCOverview","match has not started yet!")
        hideMatchEventsCard()
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