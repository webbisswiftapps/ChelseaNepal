package com.webbisswift.cfcn.v3.ui.screens.tabs.latest.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.synnapps.carouselview.ViewListener
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.newsview.WebViewActivity
import com.webbisswift.cfcn.utils.Utilities
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.v3_top_news_item_card.view.*

class TopNewsCarouselAdapter(val context: Context, val items:List<NewsStreamItem>) : ViewListener{


    override fun setViewForPosition(position: Int): View {
       if(position >= 0 && position<items.size){
        return inflateViewAndFill(items[position])
       }else return View(context)
    }


    private fun inflateViewAndFill(item:NewsStreamItem):View{

        val view = LayoutInflater.from(context).inflate(R.layout.v3_top_news_item_card, null, false)

        view.newsTitle.text = item.title
        view.newsAuthor.text = item.author
        view.newsDateTime.text = Utilities.getTimeAgo(item.pubDate)

        if(item.image != null && item.image.isNotBlank()) {

            view.newsBG.visibility = View.VISIBLE
            view.newsMainImg.visibility = View.VISIBLE
            Glide.with(view.context).load(item.image).apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3))).into(view.newsBG)
            Glide.with(view.context).load(item.image).apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(16, 0))).into(view.newsMainImg)
        }else{
            view.newsBG.visibility = View.GONE
            view.newsMainImg.visibility = View.GONE
        }

        /*if(item.type == "youtube_video") {
            mediaPlayButton.visibility = View.VISIBLE
        }else mediaPlayButton.visibility = View.GONE */
        view.setOnClickListener {
            onClick(item)
        }

        return view

    }


     fun onClick(item:NewsStreamItem) {

            val i = Intent(context, WebViewActivity::class.java)
            i.putExtra("URL", item.link)

            if (item.type == "youtube_video") {
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                try {
                    context.startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    context.startActivity(i)
                }
            } else context.startActivity(i)

        }


}