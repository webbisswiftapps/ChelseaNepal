package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters

import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R

/**
 * Created by apple on 12/22/17.
 */

class SquadAdViewHolder(private val view: View):SquadViewHolder(view){


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

    override fun setItem(item:SquadItem, metrics:String?){}

}