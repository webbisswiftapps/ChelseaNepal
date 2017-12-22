package com.webbisswift.cfcn.ui.screens.team.fragments.transfer_out

import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.TransferOutPlayer

/**
 * Created by biswas on 22/12/2017.
 */

interface TransferOutContract{

    interface  TransferOutView: BaseView {
        fun showLoading()
        fun hideLoading()
        fun addPlayers(players:List<TransferOutPlayer>)
        fun clearList()
        fun showNoPlayers()
    }

    interface  TransferOutPresenter: BasePresenter {
        fun loadTransferOutPlayers()
    }

}