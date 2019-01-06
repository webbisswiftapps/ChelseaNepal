package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 22/12/2017.
 */

class SquadInjuredPlayerVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){

    val injuredPlayer: TextView
    val injuryDesc: TextView
    val endDate:TextView
    val playerImg:ImageView


    init{
        injuredPlayer = this.view.findViewById(R.id.injuredPlayer)
        injuryDesc = this.view.findViewById(R.id.injuryDescription)
        endDate = this.view.findViewById(R.id.expectedReturn)
        playerImg = this.view.findViewById(R.id.playerImg)

        view.setOnClickListener {
            onclick.onClick(view, adapterPosition)
        }
    }


    override fun setItem(item: SquadItem) {
        val injured = item.injuredPlayer

        injuredPlayer.text = injured?.player?.data?.common_name
        injuryDesc.text = injured?.description

        if(!injured?.end_date.isNullOrEmpty()) {
            endDate.text = "Expected to return on ${injured?.end_date}"
        }else endDate.text = "Return unknown"

        Glide.with(playerImg).load(injured?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)

    }
}