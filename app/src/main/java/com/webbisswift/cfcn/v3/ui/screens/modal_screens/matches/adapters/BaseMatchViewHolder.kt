package com.webbisswift.cfcn.v3.ui.screens.modal_screens.matches.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.webbisswift.cfcn.domain.model.v2.SMMatch

/**
 * Created by apple on 12/7/17.
 */

abstract class BaseMatchViewHolder(v: View): RecyclerView.ViewHolder(v){

    abstract fun setMatch(match: SMMatch?)

    interface ItemClickInterface{
        fun onClickItem(item:Int)
    }
}