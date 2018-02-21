package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMClubTrophies
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments.adapters.TrophiesRVAdapter
import kotlinx.android.synthetic.main.club_info_fragment.*
import kotlinx.android.synthetic.main.club_trophies_fragment.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.ad_card_small_overview.*


/**
 * Created by apple on 2/16/18.
 */


class ClubTrophiesFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.club_trophies_fragment, null , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        setupRVS()
        loadTrophies()
        //etListeners()
        loadAds()
    }

    lateinit var plRVAdapter:TrophiesRVAdapter
    lateinit var eurRVAdapter:TrophiesRVAdapter
    lateinit var lgcRVAdapter:TrophiesRVAdapter
    lateinit var facRVAdapter:TrophiesRVAdapter
    lateinit var csRVAdapter:TrophiesRVAdapter
    private fun setupRVS(){


        plRVAdapter = TrophiesRVAdapter(context)
        eurRVAdapter = TrophiesRVAdapter(context)
        lgcRVAdapter = TrophiesRVAdapter(context)
        facRVAdapter = TrophiesRVAdapter(context)
        csRVAdapter = TrophiesRVAdapter(context)

        plTrophiesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        eurTrophiesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lgcTrophiesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        facTrophiesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        csTrophiesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        plTrophiesRV.adapter = plRVAdapter
        eurTrophiesRV.adapter = eurRVAdapter
        lgcTrophiesRV.adapter = lgcRVAdapter
        facTrophiesRV.adapter = facRVAdapter
        csTrophiesRV.adapter = csRVAdapter

       PagerSnapHelper().attachToRecyclerView(plTrophiesRV)
        PagerSnapHelper().attachToRecyclerView(eurTrophiesRV)
        PagerSnapHelper().attachToRecyclerView(lgcTrophiesRV)
        PagerSnapHelper().attachToRecyclerView(facTrophiesRV)
        PagerSnapHelper().attachToRecyclerView(csTrophiesRV)


    }

    private fun setListeners(){

    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
    }

    private fun loadTrophies(){
        showLoading()
        val ref = FirebaseDatabase.getInstance().getReference("v2/trophies")
        ref.keepSynced(true)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                uefaRankingCard?.visibility = View.GONE
                showLoadError()
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                try {
                    val ranking: SMClubTrophies? = snapshot?.getValue<SMClubTrophies>(SMClubTrophies::class.java)
                    presentUefaRanking(ranking)
                }catch (e:Exception){
                    e.printStackTrace()
                    showLoadError()
                }
            }
        })

    }


    private fun presentUefaRanking(trophies:  SMClubTrophies?){
        if(trophies != null){
            hideLoading()

            plRVAdapter.addTrophies(trophies.pl)
            eurRVAdapter.addTrophies(trophies.eur)
            lgcRVAdapter.addTrophies(trophies.lgc)
            facRVAdapter.addTrophies(trophies.fac)
            csRVAdapter.addTrophies(trophies.cs)

        }else showLoadError()
    }


    private fun showLoading(){}
    private fun hideLoading(){}
    private fun showLoadError(){}


}