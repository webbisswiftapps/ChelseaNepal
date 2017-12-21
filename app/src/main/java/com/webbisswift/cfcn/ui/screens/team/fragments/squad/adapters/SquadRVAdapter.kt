package com.webbisswift.cfcn.ui.screens.team.fragments.squad.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.webview.WebViewActivity

/**
 * Created by biswas on 21/12/2017.
 */

class SquadRVAdapter(val context: Context?):
        RecyclerView.Adapter<SquadViewHolder>(){



    init {
        setHasStableIds(true)
    }

    val squad:ArrayList<SquadItem> = ArrayList()

    fun addSquadItem(item:SquadItem){
        this.squad.add(item)
    }

    fun clear(){
        squad.clear()
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SquadViewHolder {

        when(viewType) {
             0 -> {
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_title, parent, false)
                 return SquadTitleVH(squadItemView)
             }
             1->{
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_coach, parent, false)
                 return SquadCoachVH(squadItemView)
             }
            else->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_player, parent, false)
                return SquadPlayerVH(squadItemView)
            }
        }




    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        holder.setItem(squad[position])
    }


    override fun getItemCount(): Int {
        return squad.size
    }

    override fun getItemViewType(position: Int): Int {
        return squad[position].type
    }


    override fun getItemId(position: Int): Long {
        return squad[position].hashCode().toLong()
    }


}