package com.webbisswift.cfcn.ui.screens.home.fragments.matches.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/22/17.
 */


class MatchesAdViewHolder(v: View): BaseMatchViewHolder(v){

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

    override fun setMatch(match: Match?) {
    }
}