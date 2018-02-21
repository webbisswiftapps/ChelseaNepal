package com.webbisswift.cfcn.ui.screens.modal.playerdetails

import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayer
import kotlinx.android.synthetic.main.player_profile_dialog_fragment.*


/**
 * Created by apple on 2/10/18.
 */

class SquadPlayerDetailsUI:DialogFragment(){


    var player:SMPlayer? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.player_profile_dialog_fragment, null, true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(player!=null){
            displayPlayer()
        }else dismiss()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun displayPlayer(){
        playerName?.text = player?.data?.common_name
        Glide.with(context).load(player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
        playerHeight?.text = player?.data?.height
        playerWeight?.text = player?.data?.weight
        playerBorn?.text = player?.data?.birthdate
        playerCountry?.text = player?.data?.nationality
        playerPosition?.text = player?.data?.positionName
    }

}