package com.webbisswift.cfcn.ui.screens.home.fragments.matches.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/7/17.
 */



class MatchesRVAdapter(val context:Context?):
        RecyclerView.Adapter<BaseMatchViewHolder>(){


    val matches:ArrayList<Match> = ArrayList()

    fun addMatches(newMatchList:List<Match>){
        matches.addAll(newMatchList)
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseMatchViewHolder {
        if(viewType == Match.TYPE_RESULT) {
            val resultView = LayoutInflater.from(context).inflate(R.layout.layout_result_item, null, false)
            return ResultsViewHolder(resultView)
        }else{
            val resultView = LayoutInflater.from(context).inflate(R.layout.layout_fixture_item, null, false)
            return FixturesViewHolder(resultView)
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



}