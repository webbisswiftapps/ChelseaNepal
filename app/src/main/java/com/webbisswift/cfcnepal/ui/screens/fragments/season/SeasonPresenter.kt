package com.webbisswift.cfcnepal.ui.screens.fragments.home

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcnepal.base.BaseView
import com.webbisswift.cfcnepal.domain.model.*
import com.webbisswift.cfcnepal.ui.screens.fragments.season.SeasonContract
import com.webbisswift.cfcnepal.ui.screens.fragments.season.SeasonModel
import com.webbisswift.cfcnepal.utils.Utilities
import java.util.ArrayList

/**
 * Created by apple on 12/3/17.
 */

class SeasonPresenter(val model:SeasonContract.SeasonModel):SeasonContract.SeasonPresenter{

    var view:SeasonContract.SeasonView? = null




    /**
     * Non Lifecycle Methods : Load Info methods
     * **/




    override fun loadFormGuideInfo() {
        this.view?.showFormGuideLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards FormItem>>() {}
                    val formItems: List<FormItem>? = dataSnapshot.getValue(t)
                    presentTeamForm(formItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideFormPane()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToFormGuide(nextMatchListener)

    }


    override fun loadTopScorers() {
        this.view?.showTopScorersLoading()
        val tableListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards TopScorer>>() {}
                    val tableItems: List<TopScorer>? = dataSnapshot.getValue(t)
                    presentTopScorers(tableItems)
                }catch (e:Exception){
                    e.printStackTrace()
                    view?.hideTopScorerPane()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToTopScorers(tableListener)
    }


    override fun loadLeagueTable() {
        this.view?.showTableLoading()
        val tableListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards LeagueTableItem>>() {}
                    val tableItems: List<LeagueTableItem>? = dataSnapshot.getValue(t)
                    presentLeagueTable(tableItems)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideTableCard()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToLeagueTable(tableListener)
    }


    /**
     * Presentation Methods
     **/



    fun presentTeamForm(form:List<FormItem>?){
        view?.hideFormGuideLoading()
        view?.clearFormPane()
        if(form!=null && form.count() > 0){
            for(res in form){
                this.view?.addFormResult(res.result)
            }
        }else view?.hideFormPane()
    }

    fun presentTopScorers(scorers:List<TopScorer>?){

        view?.hideTopScorersLoading()
        view?.clearTopScorerPane()
        if(scorers!=null && scorers.count() > 0){
            val range = if(scorers.count()<=6) scorers.count() else 6
            for(i in 0..range){
                this.view?.addTopSocrer(scorers[i])
            }
        }else view?.hideTopScorerPane()
    }

    fun presentLeagueTable(table:List<LeagueTableItem>?){
        view?.hideTableLoading()
        view?.clearTable()
        if(table!=null && table.count() > 0){
            for(item in table){
                val isChelsea = (item.path.contentEquals("chelsea"))
                view?.addTeamToTable(item, isChelsea)
            }
        }else view?.hideTableLoading()
    }

    /**
     * LifeCycle Methods
     */

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
        this.model?.unsubscribeFromFirebase()
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as SeasonContract.SeasonView
        loadFormGuideInfo()
        loadTopScorers()
        loadLeagueTable()
    }



}