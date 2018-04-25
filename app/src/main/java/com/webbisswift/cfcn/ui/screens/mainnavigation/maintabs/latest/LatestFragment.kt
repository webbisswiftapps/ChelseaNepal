package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.matches.MatchesFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.overview.HomeFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.season.SeasonFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.MainNavigationActivity
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new.NewsStreamFragment
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterUI
import kotlinx.android.synthetic.main.fragment_latest.*

/**
 * Created by apple on 2/11/18.
 */

class LatestFragment:Fragment(){

    companion object {

        fun makeFragment(subtab:Int): LatestFragment{
            val frag = LatestFragment()
            frag.page = subtab
            return frag
        }
    }


    var page:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_latest, null, true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment(){
        setupTabs()
    }

    fun setupTabs(){
        val adapter = MainPagerAdapter(childFragmentManager)

        adapter.addFragment(HomeFragment(), "Overview")
        adapter.addFragment(NewsStreamFragment(), "News")
        adapter.addFragment(SeasonFragment(), "Season")
        adapter.addFragment(MatchesFragment(), "Matches")


        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

        viewPager.currentItem = page

    }


    fun toMatchCenter(endpoint:String){
        val mcIntent = Intent(context, MatchCenterUI::class.java)
        mcIntent.putExtra("ENDPOINT", endpoint)
        startActivity(mcIntent)
    }


    fun toCharts(){
        (activity as MainNavigationActivity).toTeamTab(1)
    }


}