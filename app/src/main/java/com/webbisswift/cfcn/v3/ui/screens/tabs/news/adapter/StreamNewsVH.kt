package com.webbisswift.cfcn.v3.ui.screens.tabs.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.utils.Utilities
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.v3_layout_news_card.view.*

class StreamNewsVH(val view: View, val listener: OnItemClickListener): RecyclerView.ViewHolder(view){

    interface OnItemClickListener{
        fun onItemClicked(position:Int)
    }


    init{



        view.setOnClickListener{
            this.listener.onItemClicked(adapterPosition)
        }

    }


    fun setNews(item: NormalizedNewsItem){


        view.newsTitle.text = item.sNewsItem?.title
        view.newsAuthor.text = item.sNewsItem?.author
        view.newsDateTime.text = Utilities.getTimeAgo(item.sNewsItem?.pubDate)

        if(item.sNewsItem?.image != null && item.sNewsItem.image.isNotBlank()) {

            view.newsBG.visibility = View.VISIBLE
            view.newsMainImg.visibility = View.VISIBLE
            Glide.with(view.context).load(item.sNewsItem.image).apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(view.newsBG)
            Glide.with(view.context).load(item.sNewsItem.image).apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(16, 0))).into(view.newsMainImg)
        }else{
            view.newsBG.visibility = View.GONE
            view.newsMainImg.visibility = View.GONE
        }

        if(item.sNewsItem?.type == "youtube_video") {
            view.newsMediaPlayBtn.visibility = View.VISIBLE
        }else view.newsMediaPlayBtn.visibility = View.GONE

    }


}