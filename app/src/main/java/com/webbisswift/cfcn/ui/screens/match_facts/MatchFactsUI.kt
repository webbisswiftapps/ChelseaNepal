package com.webbisswift.cfcn.ui.screens.match_facts

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseActivity
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.Facts
import com.webbisswift.cfcn.domain.model.FactsMatchEvent
import com.webbisswift.cfcn.domain.model.MatchStat
import kotlinx.android.synthetic.main.activity_match_facts.*
import kotlinx.android.synthetic.main.layout_match_stats_card.*
import kotlinx.android.synthetic.main.layout_match_stats_events.*
import android.view.ViewAnimationUtils



/**
 * Created by biswas on 20/12/2017.
 */

class MatchFactsUI : BaseActivity(), MatchFactsContract.MatchFactsView{

    var presenter: MatchFactsContract.MatchFactsPresenter? = null

    var shouldReveal = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_match_facts)
        initView()


    }



    override fun initView() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val model = MatchFactsModel(FirebaseDatabase.getInstance())
        this.presenter = MatchFactsPresenter(model)


    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }




    /**
     *  Match Facts Display Methods
     *  */


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

    override fun hideLastMatchFactsLoading() {
        //do nothing
    }

    override fun showLastMatchFactsNotFound() {
        Log.d("MatchFactsUI","Match facts not found..")
    }

    override fun addMatchEvent(event: FactsMatchEvent) {

        val nV = LayoutInflater.from(this).inflate(R.layout.layout_match_event_item, matchEventsHolder, false)

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


    override fun hideMatchEventsCard() {
        matchEventsCard.visibility = View.GONE
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home-> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}