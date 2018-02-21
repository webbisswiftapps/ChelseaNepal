package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague

/**
 * Created by apple on 2/12/18.
 */


abstract class BaseCompetitionsViewHolder(v: View): RecyclerView.ViewHolder(v){

    abstract fun setCompetition(competition: SMPlayingLeague?)
}