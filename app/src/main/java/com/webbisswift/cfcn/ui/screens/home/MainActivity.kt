package com.webbisswift.cfcn.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.home.fragments.overview.HomeFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.matches.MatchesFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.news.NewsFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.season.SeasonFragment
import com.webbisswift.cfcn.ui.screens.match_facts.MatchFactsUI
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main_coordinator.*
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.webbisswift.cfcn.ui.screens.team.TeamInfoActivity


/**
 * Created by apple on 12/3/17.
 */

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabs()
        setupDrawer()

    }



    fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(), R.layout.tab_overview)
        adapter.addFragment(NewsFragment(), R.layout.tab_news)
        adapter.addFragment(SeasonFragment(), R.layout.tab_season)
        adapter.addFragment(MatchesFragment(), R.layout.tab_fixtures)


        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

    }

    fun setupDrawer(){

        hamburgerMenuBtn.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        navigation_drawer.setNavigationItemSelectedListener { item: MenuItem ->  
            when(item.itemId){
                R.id.nav_players -> goToPlayers()
                else ->  true
            }
        }

    }


    fun toSeasonTab(){
        viewPager.currentItem = 2
    }

    fun goToPlayers():Boolean{
        val lmIntent = Intent(this, TeamInfoActivity::class.java)
        startActivity(lmIntent)
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun toLastMatchFacts(){
        val lmIntent = Intent(this, MatchFactsUI::class.java)
        startActivity(lmIntent)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }*/


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}