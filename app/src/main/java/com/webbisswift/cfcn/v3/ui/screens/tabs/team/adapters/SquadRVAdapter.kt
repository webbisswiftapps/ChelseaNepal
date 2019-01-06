package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayer

/**
 * Created by biswas on 21/12/2017.
 */

class SquadRVAdapter(val context: Context?):
        RecyclerView.Adapter<SquadViewHolder>(), RVItemClickListener {

    interface PlayerProfileInterface{
        fun onDisplayPlayerProfile(player:SMPlayer)
    }

    var profileListener:PlayerProfileInterface? = null

    init {
        setHasStableIds(true)
    }

    val squad:ArrayList<SquadItem> = ArrayList()

    fun setPlayerProfileClickListener(listener:PlayerProfileInterface){
        this.profileListener = listener
    }

    fun addSquadItem(item:SquadItem){
        this.squad.add(item)
    }



    fun clear(){
        squad.clear()
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {

        when(viewType) {
             SquadAdapterConstants.TYPE_TITLE -> {
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_title, parent, false)
                 return SquadTitleVH(squadItemView)
             }
             SquadAdapterConstants.TYPE_COACH->{
                 val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_team_coach_card, parent, false)
                 return SquadCoachVH(squadItemView)
             }
            SquadAdapterConstants.TYPE_SQUAD_PLAYER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_player, parent, false)
                return SquadPlayerVH(squadItemView, this)
            }
            SquadAdapterConstants.TYPE_INJURED_PLAYER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_injured_player, parent, false)
                return SquadInjuredPlayerVH(squadItemView, this)
            }
            SquadAdapterConstants.TYPE_TRANSFER_IN->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_transfer_in, parent, false)
                return SquadTransferInVH(squadItemView, this)
            }

            SquadAdapterConstants.TYPE_TRANSFER_OUT-> {
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_transfer_out, parent, false)
                return SquadTransferOutVH(squadItemView, this)
            }

            SquadAdapterConstants.TYPE_CHARTS -> {
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_chart_player, parent, false)
                return SquadChartVH(squadItemView, this)
            }

            SquadAdapterConstants.TYPE_FOOTER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_footer, parent, false)
                return SquadFooterVH(squadItemView)
            }

            SquadAdapterConstants.TYPE_HEADER->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.v3_item_squad_header, parent, false)
                return SquadFooterVH(squadItemView)
            }

            else ->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.ad_card_wide_banner_season, parent, false)
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

    override fun onClick(view: View, position: Int) {
        val item = squad[position]

        when(item.type){
            SquadAdapterConstants.TYPE_SQUAD_PLAYER->{
                showPlayerInfo(item.player?.player)
            }

            SquadAdapterConstants.TYPE_CHARTS->{
                showPlayerInfo(item.player?.player)
            }

            SquadAdapterConstants.TYPE_TRANSFER_IN->{
                showPlayerInfo(item.transferInPlayer?.player)
            }
            SquadAdapterConstants.TYPE_TRANSFER_OUT->{
                showPlayerInfo(item.transferOutPlayer?.player)
            }

            SquadAdapterConstants.TYPE_INJURED_PLAYER->{
                showPlayerInfo(item.injuredPlayer?.player)
            }


        }
    }

    fun showPlayerInfo(player:SMPlayer?){
        if(player!=null && profileListener!=null){
            profileListener?.onDisplayPlayerProfile(player)
        }
    }
}