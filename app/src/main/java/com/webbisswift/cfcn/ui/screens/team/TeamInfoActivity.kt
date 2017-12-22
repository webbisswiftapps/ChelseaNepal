package com.webbisswift.cfcn.ui.screens.team

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.home.MainPagerAdapter
import com.webbisswift.cfcn.ui.screens.team.fragments.sidelined.SidelinedFragment
import com.webbisswift.cfcn.ui.screens.team.fragments.sidelined.TransferInFragment
import com.webbisswift.cfcn.ui.screens.team.fragments.sidelined.TransferOutFragment
import com.webbisswift.cfcn.ui.screens.team.fragments.squad.SquadFragment
import kotlinx.android.synthetic.main.activity_team_info.*

/**
 * Created by biswas on 21/12/2017.
 */

class TeamInfoActivity:AppCompatActivity(){


    val teamInfoModel = TeamInfoModel(FirebaseDatabase.getInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)
        setupActionBar()
        setupTabs()
    }


    fun setupActionBar(){
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(SquadFragment(), R.layout.tab_squad)
        adapter.addFragment(SidelinedFragment(), R.layout.tab_sidelined)
        adapter.addFragment(TransferInFragment(), R.layout.tab_transfers_in)
        adapter.addFragment(TransferOutFragment(), R.layout.tab_transfers_out)

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}