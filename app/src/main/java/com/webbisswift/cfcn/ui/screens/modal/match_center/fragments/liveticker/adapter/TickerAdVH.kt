package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.liveticker.adapter

import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMComments

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
                .build()
        adView.loadAd(adRequest)
    }


    override fun setItem(item: SMComments.Comment?) {

    }
}