package com.webbisswift.cfcn.ui.screens.match_center.fragments.stats

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R

/**
 * Created by apple on 2/3/18.
 */

class MCStatsFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_match_stats_fragment, null, true)
        return view
    }
}