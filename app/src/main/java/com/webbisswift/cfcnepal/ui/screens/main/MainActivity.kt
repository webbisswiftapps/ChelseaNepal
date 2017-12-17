package com.webbisswift.cfcnepal.ui.screens.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.webbisswift.cfcnepal.R
import com.webbisswift.cfcnepal.ui.screens.fragments.home.HomeFragment
import com.webbisswift.cfcnepal.ui.screens.fragments.matches.MatchesFragment
import com.webbisswift.cfcnepal.ui.screens.fragments.news.NewsFragment
import com.webbisswift.cfcnepal.ui.screens.fragments.news.adapter.NewsAdapter
import com.webbisswift.cfcnepal.ui.screens.fragments.season.SeasonFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by apple on 12/3/17.
 */

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabs()

    }



    fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(), R.layout.tab_overview)
        adapter.addFragment(NewsFragment(), R.layout.tab_news)
        adapter.addFragment(SeasonFragment(), R.layout.tab_season)
        adapter.addFragment(MatchesFragment(), R.layout.tab_fixtures)


        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

    }


    fun toSeasonTab(){
        viewPager.currentItem = 2
    }


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }*/


}