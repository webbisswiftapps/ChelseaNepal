package com.webbisswift.cfcn.ui.screens.home.fragments.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R


/**
 * Created by apple on 12/22/17.
 */


class NewsAdViewHolder(v: View): RecyclerView.ViewHolder(v){

    private var view: View = v

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

}