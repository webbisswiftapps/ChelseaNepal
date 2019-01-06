package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters.RVItemClickListener


/**
 * Created by apple on 2/12/18.
 */


class CompetitionsAdapter(val context: Context?):
        RecyclerView.Adapter<BaseCompetitionsViewHolder>(), RVItemClickListener {

    interface CompetitionClickListener{
        fun onClickCompetition(competition:SMPlayingLeague)
    }

    var clickListener:CompetitionClickListener? = null

    val leagues:ArrayList<SMPlayingLeague> = ArrayList()

    fun addLeagues(list:List<SMPlayingLeague>){
        leagues.addAll(list)
        notifyDataSetChanged()
    }


    fun clear(){
        leagues.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCompetitionsViewHolder {
        if(viewType == 0) {
            val resultView = LayoutInflater.from(context).inflate(R.layout.layout_competition_list_item, parent, false)
            return CompLeagueVH(resultView, this)
        }else{
            val adItem = LayoutInflater.from(context).inflate(R.layout.ad_card_wide_banner_season, parent , false)
            return CompAdVH(adItem)
        }
    }


    override fun onBindViewHolder(holder: BaseCompetitionsViewHolder, position: Int) {
        holder.setCompetition(leagues[position])
    }


    override fun getItemCount(): Int {
        return leagues.size
    }


    override fun getItemViewType(position: Int): Int {
        return if(leagues[position].isIs_ad) 1 else 0
    }

    override fun onClick(view: View, position: Int) {
        val item = leagues[position]
        clickListener?.onClickCompetition(item)
    }
}