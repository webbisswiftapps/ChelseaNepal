package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R
import kotlinx.android.synthetic.main.v3_item_chart_player.view.*

/**
 * Created by apple on 2/10/18.
 */

class SquadChartVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){

    val playerName: TextView
    val playerImg: ImageView
    val playerCountry: TextView
    val statCount:TextView

    init{
        playerName = this.view.findViewById(R.id.playerName)
        playerImg = this.view.findViewById(R.id.playerImg)
        playerCountry = this.view.findViewById(R.id.playerCountry)
        statCount = this.view.findViewById(R.id.statCount)

        view.setOnClickListener {
            onclick.onClick(view, adapterPosition)
        }
    }


    override fun setItem(item: SquadItem) {
        val player = item.player
        view.playerNumber.text = String.format("%d",player?.number)
        playerName.text = player?.player?.data?.common_name
        Glide.with(playerImg).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
        playerCountry.text = player?.player?.data?.nationality

        val metric = item.metric
        if(metric != null) {
            if (metric.contentEquals("Goals"))
                statCount.text = String.format("%d", player?.goals)
            else if (metric.contentEquals("Assists"))
                statCount.text = String.format("%d", player?.assists)
            else if (metric.contentEquals("Minutes Played"))
                statCount.text = String.format("%d", player?.minutes)
            else if (metric.contentEquals("Yellow Cards"))
                statCount.text = String.format("%d", player?.yellowcards)
            else if (metric.contentEquals("Red Cards"))
                statCount.text = String.format("%d", player?.redcards)
            else if (metric.contentEquals("Second Yellow Cards"))
                statCount.text = String.format("%d", player?.yellowred)
            else if (metric.contentEquals("Appearances"))
                statCount.text = String.format("%d", player?.appearences)
            else if (metric.contentEquals("Starting 11"))
                statCount.text = String.format("%d", player?.lineups)
            else if (metric.contentEquals("Bench"))
                statCount.text = String.format("%d", player?.substitutes_on_bench)
            else if (metric.contentEquals("Substituted In"))
                statCount.text = String.format("%d", player?.substitute_in)
            else if (metric.contentEquals("Substituted Out"))
                statCount.text = String.format("%d", player?.substitute_out)
        }

    }
}