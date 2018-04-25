package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.ViewSwitcher
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetUtils
import com.twitter.sdk.android.tweetui.TweetView
import com.webbisswift.cfcn.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class TweetItemVH(v: View): RecyclerView.ViewHolder(v){

    private var view: View = v

    val tSwitcher:ViewSwitcher
    val holder: FrameLayout
    val progressView:ProgressBar


    init{

        tSwitcher = this.view.findViewById(R.id.tweetSwitcher)
        holder = this.view.findViewById(R.id.tweetHolder)
        progressView = this.view.findViewById(R.id.tweetProgress)
    }

    fun loadTweet(item:NormalizedNewsItem){
        if(item.sNewsItem != null && item.sNewsItem.tweetId != null) {

            if(tSwitcher.currentView.id != R.id.tweetProgress) tSwitcher.showPrevious()
            holder.removeAllViews()

            progressView.visibility = View.VISIBLE



            doAsync {
                TweetUtils.loadTweet(item.sNewsItem.tweetId, object : Callback<Tweet>() {
                    override fun success(result: Result<Tweet>) {

                        uiThread {
                            if (tSwitcher.currentView.id == R.id.tweetProgress) tSwitcher.showNext()
                            //tweetView.tweet = result.data
                            holder.addView(TweetView(holder.context, result.data))
                        }
                    }

                    override fun failure(exception: TwitterException) {

                        uiThread {
                            exception.printStackTrace()
                            progressView.visibility = View.GONE
                            Log.d("TweetItem", "Tweet Load failure: " + exception.localizedMessage)
                        }
                    }
                })
            }

        }
    }

}