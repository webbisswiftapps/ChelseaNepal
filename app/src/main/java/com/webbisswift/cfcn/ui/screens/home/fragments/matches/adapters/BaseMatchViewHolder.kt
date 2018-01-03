package com.webbisswift.cfcn.ui.screens.home.fragments.matches.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/7/17.
 */

abstract class BaseMatchViewHolder(v: View): RecyclerView.ViewHolder(v){

    abstract fun setMatch(match: Match?)
}