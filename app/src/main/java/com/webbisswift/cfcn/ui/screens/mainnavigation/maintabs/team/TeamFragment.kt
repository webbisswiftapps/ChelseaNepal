package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayer
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.SquadRVAdapter
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.charts.TeamChartsFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.SidelinedFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferInFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.sidelined.TransferOutFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad.SquadFragment
import com.webbisswift.cfcn.ui.screens.modal.playerdetails.SquadPlayerDetailsUI
import kotlinx.android.synthetic.main.fragment_team.*

/**
 * Created by apple on 2/11/18.
 */

class TeamFragment: Fragment(), SquadRVAdapter.PlayerProfileInterface{

    companion object {

        fun makeFragment(subtab:Int): TeamFragment {
            val frag = TeamFragment()
            frag.page = subtab
            return frag
        }
    }


    var page:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, null, true)
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

        adapter.addFragment(SquadFragment(), "Squad")
        adapter.addFragment(TeamChartsFragment(), "Leaderboard")
        adapter.addFragment(SidelinedFragment(), "Sidelined")
        adapter.addFragment(TransferInFragment(), "Transfer In")
        adapter.addFragment(TransferOutFragment(), "Transfer Out")


        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)


        viewPager.currentItem = page

    }


    override fun onDisplayPlayerProfile(player: SMPlayer) {
        val fragment = SquadPlayerDetailsUI()
        fragment.player = player
        fragment.show(childFragmentManager, "player_profile")
    }


}