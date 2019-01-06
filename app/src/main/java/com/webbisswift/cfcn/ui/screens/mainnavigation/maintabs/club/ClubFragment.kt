package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments.ClubInfoFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments.ClubTrophiesFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import kotlinx.android.synthetic.main.v3_fragment_team.*

/**
 * Created by apple on 2/16/18.
 */

class ClubFragment: Fragment(){

    companion object {

        fun makeFragment(subtab:Int): ClubFragment {
            val frag = ClubFragment()
            frag.page = subtab
            return frag
        }
    }


    var page:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club, null, true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment(){
       // setupTabs()
    }

    fun setupTabs(){
        val adapter = MainPagerAdapter(childFragmentManager)

        adapter.addFragment(ClubInfoFragment(), "Info")
        adapter.addFragment(ClubTrophiesFragment(), "Trophies")



        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
       // tabs.setupWithViewPager(viewPager)
        //adapter.setCustomViews(tabs)

        viewPager.currentItem = page

    }


}