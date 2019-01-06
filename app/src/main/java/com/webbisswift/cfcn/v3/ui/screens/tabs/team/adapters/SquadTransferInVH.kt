package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R
import kotlinx.android.synthetic.main.v3_item_squad_transfer_in.view.*

/**
 * Created by biswas on 22/12/2017.
 */


class SquadTransferInVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){



    override fun setItem(item: SquadItem) {
        try {
            val player = item.transferInPlayer

            if(player != null) {
                if (player.transfer.contentEquals("bought")) {
                    view.transferType.text = "Permanent Transfer"
                } else {
                    view.transferType.text = player!!.transfer
                }

                view.transferAmount.text = player.amount
                view.transferPlayer.text = player?.player?.data?.common_name

                view.source.text = player?.team?.data?.name
                view.transferDate.text = player?.date
                Glide.with(view.playerImg).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(view.playerImg)
                Glide.with(view.fromImg).load(player?.team?.data?.logo_path).into(view.fromImg)

            }
        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}