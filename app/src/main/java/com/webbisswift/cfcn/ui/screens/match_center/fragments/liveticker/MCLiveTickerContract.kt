package com.webbisswift.cfcn.ui.screens.match_center.fragments.liveticker

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.MatchComment

/**
 * Created by apple on 12/31/17.
 */


interface MCLiveTickerContract{

    interface MCLiveTickerView : BaseView {
        fun showTickerNotStarted(error:String)
        fun setComments(comments:List<MatchComment>)

    }

    interface  MCLiveTickerPresenter: BasePresenter {
        fun loadNextMatchInfo()
    }

}