package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.domain.model.v2.SMMatch

/**
 * Created by apple on 12/7/17.
 */



class MatchesRVAdapter(val context:Context?):
        RecyclerView.Adapter<BaseMatchViewHolder>(), BaseMatchViewHolder.ItemClickInterface{


    val matches:ArrayList<SMMatch> = ArrayList()

    fun addMatches(newMatchList:List<SMMatch>){
        matches.addAll(newMatchList)
        notifyDataSetChanged()
    }


    fun clear(){
        matches.clear()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMatchViewHolder {
        if(viewType == Match.TYPE_RESULT) {
            val resultView = LayoutInflater.from(context).inflate(R.layout.v3_score_small, null, false)
            return ResultsViewHolder(resultView, this)
        }else if(viewType == Match.TYPE_FIXTURE){
            val resultView = LayoutInflater.from(context).inflate(R.layout.v3_vs_small, null, false)
            return FixturesViewHolder(resultView, this)
        }else{
            val adItem = LayoutInflater.from(context).inflate(R.layout.ad_card_wide_banner_season, parent , false)
            return MatchesAdViewHolder(adItem)
        }
    }

    override fun onBindViewHolder(holder: BaseMatchViewHolder, position: Int) {
        holder.setMatch(matches[position])
    }


    override fun getItemCount(): Int {
        return matches.size
    }


    override fun getItemViewType(position: Int): Int {
        return matches[position].matchType
    }


    override fun onClickItem(item: Int) {

    }
}