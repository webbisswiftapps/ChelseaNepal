package com.webbisswift.cfcn.v3.ui.screens.tabs.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayer
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts.TeamChartsFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.SidelinedFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferInFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferOutFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadFragment
import com.webbisswift.cfcn.ui.screens.modal.playerdetails.SquadPlayerDetailsUI
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters.SquadRVAdapter
import kotlinx.android.synthetic.main.v3_fragment_team.*
import kotlinx.android.synthetic.main.v3_layout_latest_competitions_section.*

/**
 * Created by apple on 2/11/18.
 */

class TeamFragment: Fragment(), SquadRVAdapter.PlayerProfileInterface {

    companion object {

        fun makeFragment(subtab:Int): TeamFragment {
            val frag = TeamFragment()
            frag.page = subtab
            return frag
        }
    }


    var page:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_fragment_team, null, true)
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
        val adapter = FragmentPagerItems.with(context)

        adapter.add( "Squad", SquadFragment::class.java)
        adapter.add( "Leaderboard", TeamChartsFragment::class.java)
        adapter.add( "Injuries", SidelinedFragment::class.java)
        adapter.add( "Transfers In", TransferInFragment::class.java)
        adapter.add( "Transfers Out", TransferOutFragment::class.java)


        val viewPagerAdapter = FragmentPagerItemAdapter(childFragmentManager, adapter.create())

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = viewPagerAdapter
        tabs.setViewPager(viewPager)

        viewPager.currentItem = page

    }


    override fun onDisplayPlayerProfile(player: SMPlayer) {
        val fragment = SquadPlayerDetailsUI()
        fragment.player = player
        fragment.show(childFragmentManager, "player_profile")
    }


}