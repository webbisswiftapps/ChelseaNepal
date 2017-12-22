package com.webbisswift.cfcn.ui.screens.team.adapters

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by biswas on 21/12/2017.
 */
abstract class SquadViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    abstract fun setItem(item:SquadItem)

}