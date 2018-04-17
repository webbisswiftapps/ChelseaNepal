package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMTrophyItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.adapters.RVItemClickListener
import com.webbisswift.cfcn.ui.screens.modal.webview.WebViewActivity

/**
 * Created by apple on 2/16/18.
 */

class TrophiesRVAdapter(val context: Context?):
        RecyclerView.Adapter<TrophyViewHolder>(), RVItemClickListener {



    val leagues:ArrayList<SMTrophyItem> = ArrayList()

    fun addTrophies(list:List<SMTrophyItem>){
        leagues.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        leagues.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrophyViewHolder {
        val resultView = LayoutInflater.from(context).inflate(R.layout.trophy_card_item, parent, false)
        return TrophyViewHolder(resultView, this)
    }


    override fun onBindViewHolder(holder: TrophyViewHolder, position: Int) {
        holder.setTrophy(leagues[position])
    }


    override fun getItemCount(): Int {
        return leagues.size
    }



    override fun onClick(view: View, position: Int) {
        val item = leagues[position]
        if(item != null) {
            val i = Intent(context, WebViewActivity::class.java)
            i.putExtra("URL", item.link)
            context?.startActivity(i)
        }
    }
}