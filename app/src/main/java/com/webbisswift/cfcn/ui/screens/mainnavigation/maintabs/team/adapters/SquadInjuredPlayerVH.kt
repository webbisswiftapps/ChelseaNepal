package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 22/12/2017.
 */

class SquadInjuredPlayerVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){

    val injuredPlayer: TextView
    val injuryDesc: TextView
    val startDate:TextView
    val endDate:TextView
    val playerImg:ImageView


    init{
        injuredPlayer = this.view.findViewById(R.id.injuredPlayer)
        injuryDesc = this.view.findViewById(R.id.injuryDescription)
        startDate = this.view.findViewById(R.id.outSince)
        endDate = this.view.findViewById(R.id.expectedReturn)
        playerImg = this.view.findViewById(R.id.playerImg)

        view.setOnClickListener {
            onclick.onClick(view, adapterPosition)
        }
    }


    override fun setItem(item: SquadItem, metrics:String?) {
        val injured = item.injuredPlayer

        injuredPlayer.text = injured?.player?.data?.common_name
        injuryDesc.text = injured?.description
        startDate.text = injured?.start_date
        endDate.text = injured?.end_date

        Glide.with(playerImg).load(injured?.player?.data?.image_path).into(playerImg)

    }
}