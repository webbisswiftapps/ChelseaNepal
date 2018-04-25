package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.modal.webview.WebViewActivity
import android.content.ActivityNotFoundException
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem


/**
 * Created by apple on 12/8/17.
 */


class NewsAdapter(val context: Context?):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), StreamNewsVH.OnItemClickListener{



    init {
        setHasStableIds(true)
    }

    val news:ArrayList<NormalizedNewsItem> = ArrayList()

    fun addNewsSection(items:List<NormalizedNewsItem>){
        if(items.isNotEmpty()) {
            news.addAll(items)

            for (index in news.indices) {
                if(index > 0 && index % 4 == 0){
                    //every five items, add in an ad item
                    val adType = if (news[index].sNewsItem!!.isIs_highlighted()) AdType.LARGE else AdType.SMALL
                    val adItem = NormalizedNewsItem(null, true, adType)
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




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            if(viewType == NewsStreamItem.NEWS_ARTICLE_HIGHLIGHTED || viewType == NewsStreamItem.YOUTUBE_VIDEO) {
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item, parent, false)
                return StreamNewsVH(newsItemView, this)
            }else if(viewType == NewsStreamItem.NEWS_ARTICLE){
                val newsItemView = LayoutInflater.from(context).inflate(R.layout.layout_news_item_small, parent, false)
                return StreamNewsVH(newsItemView, this)
            }else if(viewType == NewsStreamItem.TWEET){
                val tweetView = LayoutInflater.from(context).inflate(R.layout.layout_news_item_tweet, parent, false)
                return TweetItemVH(tweetView)
            }else if(viewType == NewsStreamItem.AD_LARGE){
                val adItem = LayoutInflater.from(context).inflate(R.layout.layout_news_ad_item, parent , false)
                return NewsAdViewHolder(adItem)
            }else{
                val adItem = LayoutInflater.from(context).inflate(R.layout.layout_news_ad_item_large, parent , false)
                return NewsAdViewHolder(adItem)
            }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewType = getItemViewType(position)
        if(viewType == NewsStreamItem.NEWS_ARTICLE_HIGHLIGHTED || viewType == NewsStreamItem.NEWS_ARTICLE || viewType == NewsStreamItem.YOUTUBE_VIDEO) {
            (holder as? StreamNewsVH)?.setNews(news[position])
        }else if(viewType == NewsStreamItem.TWEET){
            (holder as? TweetItemVH)?.loadTweet(news[position])
        }
    }


    override fun getItemCount(): Int {
        return news.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = news[position]
         if(item.sNewsItem != null){
            return item.sNewsItem.itemTypeId
        }else if(item.adType == AdType.SMALL){
            return NewsStreamItem.AD_SMALL
        }else{
            return NewsStreamItem.AD_LARGE
        }

    }


    override fun getItemId(position: Int): Long {
        return news[position].hashCode().toLong()
    }

    override fun onItemClicked(position:Int){

        val item = news[position]
        if(item.sNewsItem != null) {
            val url = item.sNewsItem.link
            var i = Intent(context, WebViewActivity::class.java)
            i.putExtra("URL", url)

            if (item.sNewsItem.type == "youtube_video") {
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