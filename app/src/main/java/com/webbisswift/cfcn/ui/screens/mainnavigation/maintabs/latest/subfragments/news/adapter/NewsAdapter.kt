package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.modal.webview.WebViewActivity
import android.content.ActivityNotFoundException


/**
 * Created by apple on 12/8/17.
 */


class NewsAdapter(val context: Context?):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), NewsViewHolder.OnItemClickListener{



    init {
        setHasStableIds(true)
    }

    val news:ArrayList<NormalizedNewsItem> = ArrayList()

    fun addNewsSection(items:List<NormalizedNewsItem>){
        if(items.isNotEmpty()) {
            news.addAll(items)

            for (index in news.indices) {
                if(index > 0 && index % 8 == 0){
                    //every five items, add in an ad item
                    val adItem = NormalizedNewsItem(null, true)
                    news.add(index, adItem)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun clear(){
        news.clear()
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

            if(viewType == 0) {
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item, parent, false)
                return NewsViewHolder(newsItemView, this)
            }else if(viewType == 1){
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item_small, parent, false)
                return NewsViewHolder(newsItemView, this)
            }else{
                val adItem = LayoutInflater.from(context).inflate(R.layout.layout_news_ad_item, parent , false)
                return NewsAdViewHolder(adItem)
            }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NewsViewHolder)?.setNews(news[position])
    }


    override fun getItemCount(): Int {
        return news.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = news[position]
        if(item.newsItem != null){
            if(item.newsItem.isHeading)
                return 0
            else return 1
        }else return 2

    }


    override fun getItemId(position: Int): Long {
        return news[position].hashCode().toLong()
    }

    override fun onItemClicked(position:Int){

        val item = news[position]
        if(item.newsItem != null) {
            val url = item.newsItem.finalLink
            var i = Intent(context, WebViewActivity::class.java)
            i.putExtra("URL", url)

            if (url.contains("youtube")) {
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                try {
                    context?.startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    context?.startActivity(i)
                }
            } else context?.startActivity(i)
        }
    }


}