package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 22/12/2017.
 */

class SquadTransferOutVH(private val view: View, private val onclick: RVItemClickListener):SquadViewHolder(view){

    val transferPlayer: TextView
    val transferType: TextView
    val sourceClub: TextView
    val transferDate: TextView
    val playerImg: ImageView
    val srcClubImg: ImageView


    init{
        transferPlayer = this.view.findViewById(R.id.transferPlayer)
        transferType = this.view.findViewById(R.id.transferSource)
        sourceClub = this.view.findViewById(R.id.source)
        transferDate = this.view.findViewById(R.id.transferDate)
        playerImg = this.view.findViewById(R.id.playerImg)
        srcClubImg = this.view.findViewById(R.id.fromImg)

        view.setOnClickListener {
            onclick.onClick(view, adapterPosition)
        }
    }


    override fun setItem(item: SquadItem, metrics:String?) {

        try {
            val player = item.transferOutPlayer

            var ttype = ""
            if(player!!.transfer.contentEquals("sold")){
                ttype = "Sold for "+player.amount
            }else ttype = player.transfer

            transferPlayer.text = player?.player?.data?.common_name
            transferType.text = ttype

            sourceClub.text = player?.team?.data?.name
            transferDate.text = player?.date
            Glide.with(playerImg).load(player?.player?.data?.image_path).into(playerImg)
            Glide.with(srcClubImg).load(player?.team?.data?.logo_path).into(srcClubImg)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}