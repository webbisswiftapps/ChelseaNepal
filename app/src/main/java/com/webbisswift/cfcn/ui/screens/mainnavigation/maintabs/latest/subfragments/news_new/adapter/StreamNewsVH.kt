package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.utils.Utilities

class StreamNewsVH(v: View, val listener:StreamNewsVH.OnItemClickListener): RecyclerView.ViewHolder(v){

    interface OnItemClickListener{
        fun onItemClicked(position:Int)
    }

    private var view: View = v

    val newsTitle: TextView
    val newsSource: TextView
    val newsThumb: ImageView
    val newsImage:FrameLayout?
    var newsTime: TextView
    var mediaPlayButton: ImageButton

    init{

        newsTitle = this.view.findViewById(R.id.newsTitle)
        newsSource = this.view.findViewById(R.id.newsAuthor)
        newsThumb = this.view.findViewById(R.id.newsThumb)
        newsTime = this.view.findViewById(R.id.newsDateTime)
        mediaPlayButton = this.view.findViewById(R.id.mediaPlay)
        newsImage = this.view.findViewById(R.id.newsImage)

        view.setOnClickListener({
            this.listener.onItemClicked(adapterPosition)
        })

    }


    fun setNews(nItem:NormalizedNewsItem){

        val item = nItem.sNewsItem
        newsTitle.text = item?.title
        newsSource.text = item?.author
        newsTime.text = Utilities.getTimeAgo(item?.pubDate)

        if(item?.image != null && item.image.isNotBlank()) {
            newsImage?.visibility = View.VISIBLE
            Glide.with(view.context).load(item?.image).into(newsThumb)
        }else{
            newsImage?.visibility = View.GONE
        }

        if(item?.type == "youtube_video") {
            mediaPlayButton.visibility = View.VISIBLE
        }else mediaPlayButton.visibility = View.GONE

    }


}