package com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker.adapter

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.MatchComment

/**
 * Created by apple on 12/31/17.
 */



class TickerCommentVH(private val view: View): LiveTickerViewHolder(view){

    val comment: TextView
    val minute: TextView

    init{
        comment = this.view.findViewById(R.id.comment)
        minute = this.view.findViewById(R.id.commentMinute)
    }


    override fun setItem(item: MatchComment?) {
        minute.text = item?.minute
        comment.text = item?.comment
    }



}