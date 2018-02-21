package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.adapter

import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague

/**
 * Created by apple on 2/12/18.
 */


class CompAdVH(v: View): BaseCompetitionsViewHolder(v){

    private var view: View = v

    val adView: AdView

    init{

        adView = this.view.findViewById(R.id.adView)
        load()
    }

    fun load(){
        val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest)
    }

    override fun setCompetition(competition: SMPlayingLeague?) {

    }
}