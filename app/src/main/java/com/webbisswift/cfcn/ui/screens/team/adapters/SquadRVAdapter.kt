package com.webbisswift.cfcn.ui.screens.team.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R

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
             SquadAdapterConstants.TYPE_TITLE -> {
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_title, parent, false)
                 return SquadTitleVH(squadItemView)
             }
             SquadAdapterConstants.TYPE_COACH->{
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_coach, parent, false)
                 return SquadCoachVH(squadItemView)
             }
            SquadAdapterConstants.TYPE_SQUAD_PLAYER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_player, parent, false)
                return SquadPlayerVH(squadItemView)
            }
            SquadAdapterConstants.TYPE_INJURED_PLAYER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_injured_player, parent, false)
                return SquadInjuredPlayerVH(squadItemView)
            }
            SquadAdapterConstants.TYPE_TRANSFER_IN->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_transfer_in, parent, false)
                return SquadTransferInVH(squadItemView)
            }

            SquadAdapterConstants.TYPE_TRANSFER_OUT-> {
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.item_squad_transfer_out, parent, false)
                return SquadTransferOutVH(squadItemView)
            }

            else ->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_ad_item, parent, false)
                return SquadAdViewHolder(squadItemView)
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