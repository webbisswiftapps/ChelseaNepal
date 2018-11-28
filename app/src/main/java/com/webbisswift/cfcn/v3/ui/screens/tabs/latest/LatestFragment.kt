package com.webbisswift.cfcn.v3.ui.screens.tabs.latest

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
import com.google.firebase.database.*
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMConstants
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.Utilities
import kotlinx.android.synthetic.main.layout_next_match_card.*
import kotlinx.android.synthetic.main.v3_card_last_match.*
import kotlinx.android.synthetic.main.v3_card_next_match.*
import kotlinx.android.synthetic.main.v3_layout_latest.*
import kotlinx.android.synthetic.main.v3_layout_latest_matches_section.*
import kotlinx.android.synthetic.main.v3_score_view.*

class LatestFragment : Fragment(){


    var nextMatchRef:DatabaseReference?  = null
    var lastMatchRef:DatabaseReference?  = null
    lateinit var blinkAnimation: Animation


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.v3_layout_latest, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
        subscribe()
    }


    fun initViews(){
        blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.blink_tween)
    }

    fun setListeners(){

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == -toolbar_layout.height + toolbar.height) {
                //toolbar is collapsed here
                appName.text = getString(R.string.latest_page)
            }else appName.text = ""
        })

        matchesScroller.post {
            matchesScroller.fullScroll(View.FOCUS_RIGHT)
        }


        lmCardView.setOnClickListener {
            toMatchCenter("last-match")
        }

        nmCardView.setOnClickListener {
            toMatchCenter("next-match")
        }
    }


    fun subscribe(){
        nextMatchRef = FirebaseDatabase.getInstance().getReference("v2/next-match")
        nextMatchRef?.keepSynced(true)
        val listener = object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val nextMatchInfo = p0.getValue<SMMatch>(SMMatch::class.java)
                setNextMatch(nextMatchInfo)
            }
        }

        nextMatchRef?.addValueEventListener(listener)


        lastMatchRef = FirebaseDatabase.getInstance().getReference("v2/last-match")
        lastMatchRef?.keepSynced(true)
        val l2 = object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val nextMatchInfo = p0.getValue<SMMatch>(SMMatch::class.java)
                setLastMatch(nextMatchInfo)
            }
        }

        lastMatchRef?.addValueEventListener(l2)

    }




    fun setNextMatch(match:SMMatch?){

        if(match != null){
            val localTeamId = match.localteam_id
            if(localTeamId == 18){
                val name = match.visitorTeam.data.name
                val logo = match.visitorTeam.data.logo_path

                nmOpponentName.text = name
                Glide.with(context!!).load(logo).into(nmOpponentLogo)
            }else{
                val name = match.localTeam.data.name
                val logo = match.localTeam.data.logo_path
                nmOpponentName.text = name
                Glide.with(context!!).load(logo).into(nmOpponentLogo)
            }

            if(match.venue != null) {
                nmStadium.text = match.venue.data.name
            }else{
                if(match.localteam_id == SMConstants.CHELSEA_TEAM_ID)
                    nmStadium.text = "Stamford Bridge"
                else nmStadium.text = "Away"
            }


            val startDT = match.time.starting_at.startDateTime
            if(startDT!= null){
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)


                if(timeDiff <= 0 || match.time.isLive){

                    if(liveSwitcher.currentView.id == R.id.nmCountDownPane) liveSwitcher.showNext()

                    nmOpponentLogo.visibility = View.GONE
                    nmStatus.visibility = View.VISIBLE

                    nmHomeTeam.text = match.localTeam.data.name
                    nmAwayTeam.text = match.visitorTeam.data.name
                    Glide.with(context!!).load(match.localTeam.data.logo_path).into(nmHomeTeamLogo)
                    Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(nmAwayTeamLogo)


                    nmHomeScore.text = match.scores.localteam_score
                    nmAwayScore.text = match.scores.visitorteam_score

                    nmStatus.text = match.statusDesc

                    if(match.time.isLive){
                        nmStatus?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_live_bg))
                        nmStatus?.startAnimation(blinkAnimation)
                    }else{
                        nmStatus?.setBackgroundDrawable(resources.getDrawable(R.drawable.v3_card_tag_bg))
                        nmStatus?.clearAnimation()
                    }


                    if(match.time.isAfterHalfTime){
                        nmHTScores.text = match.scores.ht_score
                        nmHTScores.visibility = View.VISIBLE
                    }else nmHTScores.visibility = View.INVISIBLE

                    if(match.time.showPenalties()){

                        nmPenScore.visibility = View.VISIBLE
                        nmPenScore.text = match.scores.localteam_pen_score+" - "+match.scores.visitorteam_pen_score +" (PEN) "
                    }else nmPenScore.visibility = View.GONE

                    if(match.aggregate != null){
                        nmAggScore.visibility = View.VISIBLE

                        if(match.aggregate.data.localteam_id == match.localteam_id){
                            nmAggScore.text = match.aggregate.data.result+" (agg)"
                        }else{
                            nmAggScore.text = match.aggregate.data.result.reversed()+" (agg)"
                        }

                    }else nmAggScore.visibility = View.GONE


                }else{
                    if(liveSwitcher.currentView.id != R.id.nmCountDownPane) liveSwitcher.showNext()

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
            if(match.tv_guide_all != null) {
                var tv = match.tv_guide_all[settings.getUserCurrentCountry()]
                if (tv == null || tv.isBlank()) {
                    tv = match.tv_guide_all["International"]
                }

                if (tv != null && tv.isNotBlank()) {
                    channels.text = tv
                } else channels.text = "Not Available"
            }else channels.text = "Not Available"

        }
    }

    fun setLastMatch(match:SMMatch?){

        if(match != null){


            val startDT = match.time.starting_at.startDateTime
            if(startDT!= null){
                val timeDiff = Utilities.getTimeDifferenceFromNow(startDT)

                nmCountDown.start(timeDiff)
                lmDate.text = Utilities.getLocaleFormattedMonthOnly(startDT)
                lmTime.text = Utilities.getLocaleFormattedDayOnly(startDT)

            }

            lmCompetition.text = match.competitionName
            lmStageRound.text = match.roundDesc

            if(match.venue != null) {
                lmStadium.text = match.venue.data.name
            }else{
                if(match.localteam_id == SMConstants.CHELSEA_TEAM_ID)
                    lmStadium.text = "Stamford Bridge"
                else lmStadium.text = "Away"
            }

            lmHomeTeam.text = match.localTeam.data.name
            lmAwayTeam.text = match.visitorTeam.data.name
            Glide.with(context!!).load(match.localTeam.data.logo_path).into(lmHomeTeamLogo)
            Glide.with(context!!).load(match.visitorTeam.data.logo_path).into(lmAwayTeamLogo)

            lmHomeScore.text = match.scores.localteam_score
            lmAwayScore.text = match.scores.visitorteam_score
            lmStatus.text = match.statusDesc

            if(match.time.showPenalties()){
                lmPenScore.visibility = View.VISIBLE
                lmPenScore.text = match.scores.localteam_pen_score+" - "+match.scores.visitorteam_pen_score
            }else lmPenScore.visibility = View.GONE

            if(match.aggregate != null){
                lmAggScore.visibility = View.VISIBLE

                if(match.aggregate.data.localteam_id == match.localteam_id){
                    lmAggScore.text = match.aggregate.data.result
                }else{
                    lmAggScore.text = match.aggregate.data.result.reversed()
                }

            }else lmAggScore.visibility = View.GONE

        }
    }



    fun toMatchCenter(endpoint:String){
        val mcIntent = Intent(context, MatchCenterUI::class.java)
        mcIntent.putExtra("ENDPOINT", endpoint)
        startActivity(mcIntent)
    }



}