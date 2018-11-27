package com.webbisswift.cfcn.ui.screens.modal.compdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.*
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMLeagueCoverage
import com.webbisswift.cfcn.domain.model.v2.SMLeagueMatches
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.modal.compdetails.leaderboard.LeaderboardFragment
import com.webbisswift.cfcn.ui.screens.modal.compdetails.overview.CompetitionOverviewFragment
import com.webbisswift.cfcn.ui.screens.modal.compdetails.stats.LeagueStatsFragment

import kotlinx.android.synthetic.main.layout_competition_details.*


/**
 * Created by apple on 2/12/18.
 */

class CompetitionDetailsUI : AppCompatActivity(){

    companion object {
        fun getIntent(context:Context, leagueId:Int, name:String):Intent{
            val intent = Intent(context, CompetitionDetailsUI::class.java)
            intent.putExtra("LEAGUE_ID", leagueId)
            intent.putExtra("LEAGUE_NAME",name)
            return intent
        }
    }

    var leagueId:Int? = null
    var leagueName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_competition_details)
        initView()
    }





     fun initView() {
         val lid  = intent.getIntExtra("LEAGUE_ID", -1)
         leagueName = intent.getStringExtra("LEAGUE_NAME")

         if(lid < 0 || leagueName.isNullOrBlank()){
             finish()
         }else {
             leagueId = lid
             compTitle.text = leagueName
             loadCoverage()
             setListeners()
         }
    }


    private fun loadCoverage(){
        val ref = FirebaseDatabase.getInstance().getReference("v2/leagues/"+leagueId+"/coverage")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val coverage =  snapshot?.getValue<SMLeagueCoverage>(SMLeagueCoverage::class.java)
                setupTabs(coverage)
            }
        })
    }




    private fun setupTabs(coverage: SMLeagueCoverage?){
        val adapter = MainPagerAdapter(supportFragmentManager)

        val ovFrag = CompetitionOverviewFragment()
        ovFrag.leagueId = leagueId!!

        adapter.addFragment(ovFrag, "Overview")

        val statsFrag = LeagueStatsFragment()
        statsFrag.leagueId = leagueId!!

        adapter.addFragment(statsFrag, "Team Stats")

        if(coverage!= null && coverage.showLeaderboard()) {
            val lbFrag = LeaderboardFragment()
            lbFrag.leagueId = leagueId!!
            adapter.addFragment(lbFrag, "Leaderboard")
        }

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

}