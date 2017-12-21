package com.webbisswift.cfcn.ui.screens.team.fragments.squad.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.utils.Utilities

/**
 * Created by biswas on 21/12/2017.
 */
abstract class SquadViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    abstract fun setItem(item:SquadItem)

}