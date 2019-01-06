package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.liveticker.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.webbisswift.cfcn.domain.model.v2.SMComments

/**
 * Created by apple on 12/31/17.
 */

abstract class LiveTickerViewHolder(view: View): RecyclerView.ViewHolder(view){

    abstract fun setItem(item: SMComments.Comment?)

}