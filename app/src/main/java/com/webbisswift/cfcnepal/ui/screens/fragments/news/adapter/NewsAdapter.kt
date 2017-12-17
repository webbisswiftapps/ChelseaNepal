package com.webbisswift.cfcnepal.ui.screens.fragments.news.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcnepal.R
import com.webbisswift.cfcnepal.ui.screens.webview.WebViewActivity
import com.webbisswift.cfcnepal.utils.Utilities
import android.support.v4.content.ContextCompat.startActivity
import android.content.ActivityNotFoundException
import com.webbisswift.cfcnepal.domain.localdb.entities.DBNewsItem


/**
 * Created by apple on 12/8/17.
 */


class NewsAdapter(val context: Context?):
        RecyclerView.Adapter<NewsViewHolder>(), NewsViewHolder.OnItemClickListener{



    init {
        setHasStableIds(true)
    }

    val news:ArrayList<DBNewsItem> = ArrayList()

    fun addNewsSection(items:List<DBNewsItem>){
        if(items.isNotEmpty()) {
            news.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun clear(){
        news.clear()
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewsViewHolder {

            if(viewType == 0) {
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item, parent, false)
                return NewsViewHolder(newsItemView, this)
            }else{
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item_small, parent, false)
                return NewsViewHolder(newsItemView, this)
            }


    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setNews(news[position])
    }


    override fun getItemCount(): Int {
        return news.size
    }

    override fun getItemViewType(position: Int): Int {
        when(news[position].isHeading) {
            true -> return 0
            false -> return 1
        }
    }


    override fun getItemId(position: Int): Long {
        return news[position].finalLink.hashCode().toLong()
    }

    override fun onItemClicked(position:Int){

        val url = news[position].finalLink
        var i = Intent(context, WebViewActivity::class.java)
        i.putExtra("URL", url)

        if(url.contains("youtube")){
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                context?.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context?.startActivity(i)
            }
        }else context?.startActivity(i)
    }


}