package com.webbisswift.cfcn.ui.screens.team.fragments.transfer_in

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.TrasnferInPlayer

/**
 * Created by biswas on 22/12/2017.
 */


interface TransferInContract{

    interface  TransferInView: BaseView {
        fun showLoading()
        fun hideLoading()
        fun addPlayers(players:List<TrasnferInPlayer>)
        fun clearList()
        fun showNoPlayers()
    }

    interface  TransferInPresenter: BasePresenter {
        fun loadTransferInPlayers()
    }

}