package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.transfer_in

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMTransferItem

/**
 * Created by biswas on 22/12/2017.
 */

class TransferInPresenter(val model: TransferInContract.TransferInModel) : TransferInContract.TransferInPresenter{

    var view: TransferInContract.TransferInView? = null

    override fun loadTransferInPlayers() {
        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMTransferItem>>() {}
                    val formItems: List<SMTransferItem>? = dataSnapshot.getValue(t)
                    presentTxIn(formItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoPlayers()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToTxIn(nextMatchListener)
    }

    fun presentTxIn(tx: List<SMTransferItem>?){
        view?.hideLoading()

        if(tx != null && tx.isNotEmpty()){
            view?.clearList()
            view?.addPlayers(tx.take(20))
        }else view?.showNoPlayers()

    }

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as TransferInContract.TransferInView
        loadTransferInPlayers()
    }

    override fun detachView() {
        this.view = null
        this.model.unsubscribeFromTxIn()
    }
}