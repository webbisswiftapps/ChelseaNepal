package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_out

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMTransferItem

/**
 * Created by biswas on 22/12/2017.
 */

interface TransferOutContract{

    interface  TransferOutView: BaseView {
        fun showLoading()
        fun hideLoading()
        fun addPlayers(players:List<SMTransferItem>)
        fun clearList()
        fun showNoPlayers()
    }

    interface  TransferOutPresenter: BasePresenter {
        fun loadTransferOutPlayers()
    }

    interface TransferOutModel{
        fun subscribeToTxOut(listener: ValueEventListener)
        fun unsubscribeFromTxOut()
    }

}