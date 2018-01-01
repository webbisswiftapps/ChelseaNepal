package com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.adapter

import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.MatchComment

/**
 * Created by apple on 12/31/17.
 */


class TickerAdVH(private val view: View): LiveTickerViewHolder(view){

    val adView: AdView

    init{

        adView = this.view.findViewById(R.id.adView)
        load()
    }

    fun load(){
        val adRequest = AdRequest.Builder()
                .addTestDevice("D97506CE44741D62F39273476ECCCA35")
                .addTestDevice("C59EB2BE510BBC21EF6D8F6A3D585248")
                .build()
        adView.loadAd(adRequest)
    }


    override fun setItem(item: MatchComment?) {

    }
}