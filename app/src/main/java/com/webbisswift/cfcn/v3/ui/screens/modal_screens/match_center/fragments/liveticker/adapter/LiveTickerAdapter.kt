package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.liveticker.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMComments

/**
 * Created by apple on 12/31/17.
 */

class LiveTickerAdapter(val context: Context?): RecyclerView.Adapter<LiveTickerViewHolder>(){

    val comments:ArrayList<TickerItem> = ArrayList()

    fun addComments(items: List<SMComments.Comment>){
         comments.clear()
        if(items.size > 1) {
            for ((index, value) in items.withIndex()) {

                var type = TickerAdapterConstants.TYPE_NORMAL
                if(value.isGoal)
                    type = TickerAdapterConstants.TYPE_GOAL
                else if(value.isImportant)
                    type = TickerAdapterConstants.TYPE_IMPORTANT

                val commentItem = TickerItem(type, value)
                comments.add(commentItem)

                if(index > 7 && index % 8 == 0){
                    val adItem = TickerItem(TickerAdapterConstants.TYPE_AD, null)
                    comments.add(adItem)
                }
            }
            this.notifyDataSetChanged()
        }else{
            val value = items[0]
            var type = TickerAdapterConstants.TYPE_NORMAL
            if(value.isGoal)
                type = TickerAdapterConstants.TYPE_GOAL
            else if(value.isImportant)
                type = TickerAdapterConstants.TYPE_IMPORTANT

            val commentItem = TickerItem(type, value)
            comments.add(commentItem)
            val adItem = TickerItem(TickerAdapterConstants.TYPE_AD, null)
             comments.add(adItem)
            this.notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveTickerViewHolder {

        when(viewType){
            TickerAdapterConstants.TYPE_NORMAL ->{
                val view = LayoutInflater.from(context).inflate(R.layout.v3_layout_ticker_normal_item, parent, false)
                return TickerCommentVH(view)
            }

            TickerAdapterConstants.TYPE_IMPORTANT ->{
                val view = LayoutInflater.from(context).inflate(R.layout.v3_layout_ticker_important_item, parent, false)
                return TickerCommentVH(view)
            }

            TickerAdapterConstants.TYPE_GOAL ->{
                val view = LayoutInflater.from(context).inflate(R.layout.v3_layout_ticker_goal_item, parent, false)
                return TickerCommentVH(view)
            }

            else ->{
                val squadItemView = LayoutInflater.from(context).inflate(R.layout.ad_card_wide_banner_season, parent, false)
                return TickerAdVH(squadItemView)
            }
        }
    }

    override fun onBindViewHolder(holder: LiveTickerViewHolder, position: Int) {
        holder?.setItem(comments[position].item)
    }


    override fun getItemViewType(position: Int): Int {
        val item = comments[position]
        return item.type
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}