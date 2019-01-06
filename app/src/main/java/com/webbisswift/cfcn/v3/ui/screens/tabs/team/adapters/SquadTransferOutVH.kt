package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.v3_item_squad_transfer_out.view.*

/**
 * Created by biswas on 22/12/2017.
 */

class SquadTransferOutVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){



    override fun setItem(item: SquadItem) {

        try {
            val player = item.transferOutPlayer

            if(player != null) {
                if (player.transfer.contentEquals("bought")) {
                    view.transferType.text = "Permanent Transfer"
                } else {
                    view.transferType.text = player!!.transfer
                }

                view.transferAmount.text = player.amount
                view.transferPlayer.text = player?.player?.data?.common_name

                view.dest.text = player?.team?.data?.name
                view.transferDate.text = player?.date
                Glide.with(view.playerImg).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(view.playerImg)
                Glide.with(view.toImg).load(player?.team?.data?.logo_path).into(view.toImg)

            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}