package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.liveticker.adapter

import android.view.View
import android.widget.TextView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMComments

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


    override fun setItem(item: SMComments.Comment?) {
        if(item?.extra_minute != null && item?.extra_minute > 0)
            minute.text = String.format("%d+%d'", item.minute, item.extra_minute)
        else minute.text = String.format("%d'", item?.minute)
        comment.text = item?.comment
    }



}