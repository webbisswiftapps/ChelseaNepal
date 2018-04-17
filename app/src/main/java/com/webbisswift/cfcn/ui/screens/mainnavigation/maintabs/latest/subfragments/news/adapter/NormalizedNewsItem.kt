package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.news.adapter

import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem

/**
 * Created by apple on 12/8/17.
 */

enum class AdType{
    SMALL, LARGE
}

data class NormalizedNewsItem(val newsItem:DBNewsItem?, val isAd:Boolean, val adType:AdType)