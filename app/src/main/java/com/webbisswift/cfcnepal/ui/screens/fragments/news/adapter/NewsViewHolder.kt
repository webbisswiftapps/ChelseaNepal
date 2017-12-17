package com.webbisswift.cfcnepal.ui.screens.fragments.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcnepal.R
import com.webbisswift.cfcnepal.domain.localdb.entities.DBNewsItem
import com.webbisswift.cfcnepal.utils.Utilities

/**
 * Created by apple on 12/8/17.
 */


class NewsViewHolder(v: View, val listener:NewsViewHolder.OnItemClickListener): RecyclerView.ViewHolder(v){

    interface OnItemClickListener{
        fun onItemClicked(position:Int)
    }

    private var view: View = v

    val newsTitle: TextView
    val newsSource: TextView
    val newsThumb: ImageView
    var newsTime: TextView
    var mediaPlayButton:ImageButton



    init{

        newsTitle = this.view.findViewById(R.id.newsTitle)
        newsSource = this.view.findViewById(R.id.newsAuthor)
        newsThumb = this.view.findViewById(R.id.newsThumb)
        newsTime = this.view.findViewById(R.id.newsDateTime)
        mediaPlayButton = this.view.findViewById(R.id.mediaPlay)

        view.setOnClickListener({
            this.listener.onItemClicked(adapterPosition)
        })

    }


    fun setNews(item:DBNewsItem){

        newsTitle.text = item.title.trim()
        newsSource.text = item.authorName.trim()
        newsTime.text = Utilities.getTimeAgo(item.getPubDate())
        Glide.with(view.context).load(item.thumbURL).into(newsThumb)

        if(item.isVideo)
            mediaPlayButton.visibility = View.VISIBLE
        else mediaPlayButton.visibility = View.GONE

    }






}