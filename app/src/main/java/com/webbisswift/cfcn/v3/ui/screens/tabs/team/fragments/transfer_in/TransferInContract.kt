package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in

import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMTransferItem

/**
 * Created by biswas on 22/12/2017.
 */


interface TransferInContract{

    interface  TransferInView: BaseView {
        fun showLoading()
        fun hideLoading()
        fun addPlayers(players:List<SMTransferItem>)
        fun clearList()
        fun showNoPlayers()
    }

    interface  TransferInPresenter: BasePresenter {
        fun loadTransferInPlayers()
    }

    interface TransferInModel{
        fun subscribeToTxIn(listener: ValueEventListener)
        fun unsubscribeFromTxIn()
    }

}