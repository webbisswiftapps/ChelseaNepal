package com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.*
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMLeagueCoverage
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.leaderboard.LeaderboardFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.overview.CompetitionOverviewFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.stats.LeagueStatsFragment

import kotlinx.android.synthetic.main.v3_competition_activity_modal.*


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
        setContentView(R.layout.v3_competition_activity_modal)
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
        val adapter = FragmentPagerItems.with(this)

        val bundler =  Bundler()
        bundler.putInt("league", leagueId!!)
        bundler.putString("comp_name", leagueName!!)
        adapter.add( "Overview", CompetitionOverviewFragment::class.java, bundler.get())




        if(coverage!= null && coverage.showLeaderboard()) {
            adapter.add( "Leaderboard", LeaderboardFragment::class.java, bundler.get())
        }

        val viewPagerAdapter = FragmentPagerItemAdapter(supportFragmentManager, adapter.create())

        viewPager.offscreenPageLimit = 3
        viewPager.adapter = viewPagerAdapter
        tabs.setViewPager(viewPager)

    }

    private fun setListeners(){
        backButton.setOnClickListener {
            finish()
        }
    }

}