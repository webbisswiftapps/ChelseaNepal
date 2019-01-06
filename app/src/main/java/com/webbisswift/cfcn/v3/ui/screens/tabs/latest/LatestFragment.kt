package com.webbisswift.cfcn.v3.ui.screens.tabs.latest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.Utilities
import com.webbisswift.cfcn.v3.ui.screens.tabs.latest.adapters.TopNewsCarouselAdapter
import com.webbisswift.cfcn.v3.ui.screens.tabs.latest.fragments.competitions.CompFragment
import kotlinx.android.synthetic.main.v3_card_last_match.*
import kotlinx.android.synthetic.main.v3_card_next_match.*
import kotlinx.android.synthetic.main.v3_layout_latest.*
import kotlinx.android.synthetic.main.v3_layout_latest_competitions_section.*
import kotlinx.android.synthetic.main.v3_layout_latest_matches_section.*
import kotlinx.android.synthetic.main.v3_layout_latest_news_section.*
import kotlinx.android.synthetic.main.v3_score_view.*
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.webbisswift.cfcn.domain.model.v2.*
import com.webbisswift.cfcn.v3.ui.screens.mainnav.MainNavigationActivity
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.MatchesActivity
import kotlinx.android.synthetic.main.v3_card_topcharts_item.view.*
import kotlinx.android.synthetic.main.v3_goal_scorer_small.view.*
import kotlinx.android.synthetic.main.v3_item_topcharts_runners_up.view.*
import kotlinx.android.synthetic.main.v3_layout_latest_topcharts_section.*
import kotlinx.android.synthetic.main.v3_loading_overlay.view.*


class LatestFragment : Fragment() {

    lateinit var blinkAnimation: Animation
    lateinit var mViewModel: LatestViewModel

    /** ----------------- Lifecycle Methods ----------------- **/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.v3_layout_latest, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
        mViewModel = ViewModelProviders.of(this).get(LatestViewModel::class.java)
    }


    override fun onResume() {
        super.onResume()

        mViewModel.getLastMatch().observe(this, Observer { lastMatch -> setLastMatch(lastMatch) })
        mViewModel.getNextMatch().observe(this, Observer { nextMatch -> setNextMatch(nextMatch) })
        mViewModel.getTopNews().observe(this, Observer { news -> setTopNews(news) })
        mViewModel.getPlayingLeagues().observe(this, Observer { leagues -> setupCompetitionsPager(leagues) })
        mViewModel.getSquad().observe(this, Observer { squad -> setupTopCharts(squad) })

    }


    /** -----------------  Lifecycle Methods End  -----------------  **/


    fun initViews() {
        blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.blink_tween)
    }

    fun setListeners() {

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == -toolbar_layout.height + toolbar.height) {
                //toolbar is collapsed here
                appName.text = getString(R.string.latest_page)
            } else appName.text = ""
        })



        btn_all_matches.setOnClickListener {
            startActivity(Intent(this.context, MatchesActivity::class.java))
        }

        btn_all_news.setOnClickListener {
            (activity as MainNavigationActivity).toNewsTab()
        }


        matchesScroller?.post {
            matchesScroller?.fullScroll(View.FOCUS_RIGHT)
        }

        lmCardView.setOnClickListener {
            toMatchCenter("last-match")
        }

        nmCardView.setOnClickListener {
            toMatchCenter("next-match")
        }

    }






    /** --------------- UI Update Methods ----------------- **/

    /**
     * setNextMatch: Updates Next match card.
     */
    private fun setNextMatch(match: SMMatch?) {

        if (match != null) {
            val localTeamId = match.localteam_id
            if (localTeamId == 18) {
                val name = match.visitorTeam.data.name
                val logo = match.visitorTeam.data.logo_path

                nmOpponentName.text = name
                Glide.with(context!!).load(logo).into(nmOpponentLogo)
            } else {
                val name = match.localTeam.data.name
                val logo = match.localTeam.data.logo_path
                nmOpponentName.text = name
                Glide.with(context!!).load(logo).into(nmOpponentLogo)
            }

            if (match.venue != null) {
                nmStadium.text = match.venue.data.name
            } else {
                if (match.localteam_id == SMConstants.CHELSEA_TEAM_ID)
                    nmStadium.text = "Stamford Bridge"
                else nmStadium.text = "Away"
            }


            val startDT = match.time.starting_at.startDateTime
            if (startDT != null) {
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)


                if (timeDiff <= 0 || match.time.isLive) {

                    if (liveSwitcher.currentView.id == R.id.nmCountDownPane) liveSwitcher.showNext()

                    nmOpponentLogo.visibility = View.GONE
                    nmStatus.visibility = View.VISIBLE

                    nmHomeTeam.text = match.localTeam.data.minimalName
                    nmAwayTeam.text = match.visitorTeam.data.minimalName
                    Glide.with(context!!).load(match.localTeam.data.logo_path).into(nmHomeTeamLogo)
                    Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(nmAwayTeamLogo)


                    nmHomeScore.text = match.scores.localteam_score
                    nmAwayScore.text = match.scores.visitorteam_score

                    nmStatus.text = match.statusDesc

                    if (match.time.isLive) {
                        nmStatus?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_live_bg))
                        nmStatus?.startAnimation(blinkAnimation)
                    } else {
                        nmStatus?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_tag_bg))
                        nmStatus?.clearAnimation()
                    }


                    if (match.time.isAfterHalfTime) {
                        nmHTScores.text = match.scores.ht_score
                        nmHTScores.visibility = View.VISIBLE
                    } else nmHTScores.visibility = View.INVISIBLE

                    if (match.time.showPenalties()) {

                        nmPenScore.visibility = View.VISIBLE
                        nmPenScore.text = match.scores.localteam_pen_score + " - " + match.scores.visitorteam_pen_score + " (pen) "
                    } else nmPenScore.visibility = View.GONE

                    if (match.aggregate != null) {
                        nmAggScore.visibility = View.VISIBLE

                        if (match.aggregate.data.localteam_id == match.localteam_id) {
                            nmAggScore.text = match.aggregate.data.result + " (agg)"
                        } else {
                            nmAggScore.text = match.aggregate.data.result.reversed() + " (agg)"
                        }

                    } else nmAggScore.visibility = View.GONE


                } else {
                    if (liveSwitcher.currentView.id != R.id.nmCountDownPane) liveSwitcher.showNext()

                    nmOpponentLogo.visibility = View.VISIBLE
                    nmStatus.visibility = View.GONE

                    nmCountDown.start(timeDiff)
                    nmDate.text = Utilities.getLocaleFormattedDateOnly(startDT)
                    nmTime.text = Utilities.getLocaleFormattedTimeOnly(startDT)
                }
            }

            nmCompetition.text = match.competitionName
            nmStageRound.text = match.roundDesc

            val settings = SettingsHelper(context!!)
            if (match.tv_guide_all != null) {
                var tv = match.tv_guide_all[settings.getUserCurrentCountry()]
                if (tv == null || tv.isBlank()) {
                    tv = match.tv_guide_all["International"]
                }

                if (tv != null && tv.isNotBlank()) {
                    channels.text = tv
                } else channels.text = "Not Available"
            } else channels.text = "Not Available"

            nmLoading.visibility = View.GONE
        }else{
            nmLoading.visibility = View.VISIBLE
            Glide.with(this).asGif().load(R.raw.failure_eden).into(nmLoading.progressGIF)
        }
    }

    /**
     * setLastMatch: Updates Last match card.
     */
    private fun setLastMatch(match: SMMatch?) {

        if (match != null) {


            val startDT = match.time.starting_at.startDateTime
            if (startDT != null) {
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)

                nmCountDown.start(timeDiff)
                lmDate.text = Utilities.getLocaleFormattedMonthOnly(startDT)
                lmTime.text = Utilities.getLocaleFormattedDayOnly(startDT)

            }

            lmCompetition.text = match.competitionName
            lmStageRound.text = match.roundDesc

            if (match.venue != null) {
                lmStadium.text = match.venue.data.name
            } else {
                if (match.localteam_id == SMConstants.CHELSEA_TEAM_ID)
                    lmStadium.text = "Stamford Bridge"
                else lmStadium.text = "Away"
            }

            lmHomeTeam.text = match.localTeam.data.minimalName
            lmAwayTeam.text = match.visitorTeam.data.minimalName
            Glide.with(context!!).load(match.localTeam.data.logo_path).into(lmHomeTeamLogo)
            Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(lmAwayTeamLogo)

            lmHomeScore.text = match.scores.localteam_score
            lmAwayScore.text = match.scores.visitorteam_score
            lmStatus.text = match.statusDesc

            if (match.time.showPenalties()) {
                lmPenScore.visibility = View.VISIBLE
                lmPenScore.text = match.scores.localteam_pen_score + " - " + match.scores.visitorteam_pen_score
            } else lmPenScore.visibility = View.GONE

            if (match.aggregate != null) {
                lmAggScore.visibility = View.VISIBLE

                if (match.aggregate.data.localteam_id == match.localteam_id) {
                    lmAggScore.text = match.aggregate.data.result
                } else {
                    lmAggScore.text = match.aggregate.data.result.reversed()
                }

            } else lmAggScore.visibility = View.GONE


            val goals = match.events.data.filter { it.isGoal }
            lmScorersHome.removeAllViews()
            lmScorersAway.removeAllViews()
            for(goal in goals.reversed()){

                var minute= "${goal.minute}'"
                if(goal.extra_minute > 0 ){
                    minute = "${goal.minute}+${goal.extra_minute}'"
                }

                var player = ""
                if(goal.type != null && goal.type!!.contentEquals("goal")) player = goal.player_name
                else if(goal.type != null && goal.type!!.contentEquals("penalty")) player = "${goal.player_name} (pen)"
                else if(goal.type != null && goal.type!!.contentEquals("own_goal")) player = "${goal.player_name} (o.g.)"

                val view = layoutInflater.inflate(R.layout.v3_goal_scorer_small, null, false)
                view.eventTime.text = minute
                view.eventPlayer.text = player


                if(goal.team_id != null && goal.team_id!!.contentEquals("${match.localteam_id}")){
                    lmScorersHome.addView(view)
                }else{
                    lmScorersAway.addView(view)
                }

            }

            lmLoading.visibility = View.GONE
        }else lmLoading.visibility = View.VISIBLE
    }

    /**
     * setTopNews: Sets top 5 news
     */

    private fun setTopNews(news:List<NewsStreamItem>?){

        if(news != null){
            tnLoading.visibility = View.GONE
            val adapter = TopNewsCarouselAdapter(context!!, news)
            topNewsCarousel.setViewListener(adapter)
            topNewsCarousel.pageCount = news.size
         }else{
            tnLoading.visibility = View.VISIBLE

        }
    }


    private fun setupCompetitionsPager(leagues:List<SMPlayingLeague>?){

        if(leagues != null && leagues.isNotEmpty()) {

            competitionsSection.visibility = View.VISIBLE
            coLoading.visibility = View.GONE
            val items = FragmentPagerItems.with(context)



            for(league in leagues.sortedBy { SMLeague.getOrderById(it.id) }) {
                val bundler =  Bundler()
                bundler.putInt("league", league.id)
                bundler.putBoolean("is_cup", league.isIs_cup)
                bundler.putString("comp_name", league.name)
                items.add(league.name, CompFragment::class.java, bundler.get())
            }

            val viewPagerAdapter = FragmentPagerItemAdapter(childFragmentManager, items.create())
            competitionsViewPager.adapter = viewPagerAdapter
            viewpagertab.setViewPager(competitionsViewPager)

        }else{
            competitionsSection.visibility = View.GONE
            coLoading.visibility = View.VISIBLE
        }
    }


    private fun setupTopCharts(squad:SMSquad?){

        if(squad != null){

            topChartsHolder.removeAllViews()
            val topScorers = squad.data.sortedByDescending { it.goals }
            makeTCCard(topScorers.take(5), "Goal")

            val topAssists = squad.data.sortedByDescending { it.assists }
            makeTCCard(topAssists.take(5), "Assist")

            val topAppearances = squad.data.sortedByDescending { it.appearences }
            makeTCCard(topAppearances.take(5), "Appearance")

            val topYC = squad.data.sortedByDescending { it.yellowcards }
            makeTCCard(topYC.take(5), "Yellow Card")

            tcLoading.visibility = View.GONE

        }else{
            tcLoading.visibility = View.VISIBLE
        }
    }



    private fun makeTCCard(players:List<SMSquad.SMSquadItem>, tag:String){

        if(players.isNotEmpty()) {

            val tc_item = layoutInflater.inflate(R.layout.v3_card_topcharts_item, topChartsHolder, false)
            tc_item.tcStatName.text = tag

            tc_item.tcPlayerFirstName.text = players[0].player.data.firstname
            tc_item.tcPlayerLastName.text = players[0].player.data.lastname
            Glide.with(context!!).load(players[0].player.data.image_path).apply(RequestOptions.circleCropTransform()).into(tc_item.tcPlayerImage)

            tc_item.tcPlayerStatCount.text = players[0].getStatByTag(tag)

            topChartsHolder.addView(tc_item)


            //now loop and add small runners up items
            val runners = players.takeLast(4)
            for(runner in runners){

                val ru_item = layoutInflater.inflate(R.layout.v3_item_topcharts_runners_up, tc_item.runnerUpsHolder, false)
                ru_item.tcRunnerupPlayerName.text = runner.player.data.common_name
                ru_item.tcRunnerupStat.text = runner.getStatByTag(tag)
                Glide.with(context!!).load(runner.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(ru_item.tcRunnerupPlayerImage)
                tc_item.runnerUpsHolder.addView(ru_item)
            }

        }
    }

    //private fun showTopAssits(assists:Lis)
    /** -----------------  UI Update Methods End -----------------  **/


    fun toMatchCenter(endpoint: String) {
        val mcIntent = Intent(context, MatchCenterUI::class.java)
        mcIntent.putExtra("ENDPOINT", endpoint)
        startActivity(mcIntent)
    }


}