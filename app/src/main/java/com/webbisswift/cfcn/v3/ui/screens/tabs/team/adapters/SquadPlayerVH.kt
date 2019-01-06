package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 21/12/2017.
 */


class SquadPlayerVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){

    val playerNumber: TextView
    val playerName:TextView
    val playerImg: ImageView
    val playerCountry:TextView

    init{
        playerNumber = this.view.findViewById(R.id.playerNumber)
        playerName = this.view.findViewById(R.id.playerName)
        playerImg = this.view.findViewById(R.id.playerImg)
        playerCountry = this.view.findViewById(R.id.playerCountry)

        view.setOnClickListener {
            onclick.onClick(view, adapterPosition)
        }
    }


    override fun setItem(item: SquadItem) {
        val player = item.player
        if(player?.number != null && player.number > 0) {
            playerNumber.text = String.format("%d", player.number)
        }else playerNumber.text = ""

        playerName.text = player?.player?.data?.common_name
        Glide.with(playerImg).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
        playerCountry.text = player?.player?.data?.nationality
    }

}