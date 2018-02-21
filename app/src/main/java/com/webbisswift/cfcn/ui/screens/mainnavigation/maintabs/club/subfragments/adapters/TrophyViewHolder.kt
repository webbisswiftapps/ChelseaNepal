package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMTrophyItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.RVItemClickListener
import org.jetbrains.anko.find

/**
 * Created by apple on 2/16/18.
 */

class TrophyViewHolder(v: View, onclick: RVItemClickListener): RecyclerView.ViewHolder(v){

    private var view: View = v

    private val title: TextView
    private val coach:TextView
    private val result :TextView
    private val img:ImageView

    init{
        title = view.findViewById(R.id.trophyTitle)
        coach = view.findViewById(R.id.trophyCoach)
        result = view.findViewById(R.id.trophyPoints)

        img = view.findViewById(R.id.trophyImg)

        view.setOnClickListener { onclick.onClick(view, adapterPosition) }
    }


     fun setTrophy(trophy: SMTrophyItem?) {
        if(trophy != null){
            title.text = trophy.title
            coach.text = trophy.coach
            result.text = trophy.points
            Glide.with(img).load(trophy.picture).into(img)
        }
    }
}