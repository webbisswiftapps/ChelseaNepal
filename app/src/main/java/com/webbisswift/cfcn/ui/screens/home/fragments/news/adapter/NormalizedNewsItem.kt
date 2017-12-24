package com.webbisswift.cfcn.ui.screens.home.fragments.news.adapter

import com.webbisswift.cfcn.domain.localdb.entities.DBNewsItem
import org.joda.time.DateTime

/**
 * Created by apple on 12/8/17.
 */

data class NormalizedNewsItem(val newsItem:DBNewsItem?, val isAd:Boolean)