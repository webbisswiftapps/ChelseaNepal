package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news_new.adapter

import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem

/**
 * Created by apple on 12/8/17.
 */

enum class AdType{
    SMALL, LARGE
}

data class NormalizedNewsItem(val sNewsItem:NewsStreamItem?, val isAd:Boolean, val adType:AdType)