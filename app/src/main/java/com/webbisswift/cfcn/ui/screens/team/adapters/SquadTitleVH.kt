package com.webbisswift.cfcn.ui.screens.team.adapters

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R

/**
 * Created by biswas on 21/12/2017.
 */

class SquadTitleVH(private val view:View):SquadViewHolder(view){

    val title: TextView

    init{
        title = this.view.findViewById(R.id.title)
    }


    override fun setItem(item: SquadItem) {
        title.text = item.title
    }
}