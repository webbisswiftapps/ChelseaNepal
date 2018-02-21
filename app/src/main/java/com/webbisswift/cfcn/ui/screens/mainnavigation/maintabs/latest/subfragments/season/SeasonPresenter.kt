package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.overview

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.*
import com.webbisswift.cfcn.domain.model.v2.SMConstants
import com.webbisswift.cfcn.domain.model.v2.SMSquad
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.season.SeasonContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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

    override fun loadTopCharts() {
        this.view?.showChartsLoading()
        val chartListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                try {
                    val squad = dataSnapshot.getValue<SMSquad>(SMSquad::class.java)
                    presentCharts(squad)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.hideCharts()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.hideCharts()
            }
        }
        this.model.subscribeToSquad(chartListener)
    }


    override fun loadLeagueTable() {
        this.view?.showTableLoading()
        val tableListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMStandingItem>>() {}
                    val tableItems: List<SMStandingItem>? = dataSnapshot.getValue(t)
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


    fun presentLeagueTable(table:List<SMStandingItem>?){
        view?.hideTableLoading()
        view?.clearTable()
        if(table!=null && table.count() > 0){

            var cheItem:SMStandingItem? = null
            for(item in table){
                val isChelsea = (item.team_id == SMConstants.CHELSEA_TEAM_ID)
                if(isChelsea){
                    cheItem = item
                }
            }

            if(cheItem != null){
                val cpos = cheItem.position - 1
                val from = maxOf(0, cpos - 1)
                val to = minOf(table.count() - 1 ,cpos + 1 )

                for( i in from .. to){
                    val isChelsea = (i == cpos)
                    view?.addTeamToTable(table[i], isChelsea)
                }
            }

        }else view?.hideTableLoading()
    }


    fun presentCharts(squad:SMSquad?){
        if(squad != null && squad?.data.isNotEmpty()) {
            doAsync {

                try {
                    val topScorer = squad.data.sortedWith(compareBy { it.goals }).reversed()[0]
                    val topAssist = squad.data.sortedWith(compareBy { it.assists }).reversed()[0]
                    val ta =  squad.data.sortedWith(compareBy { it.appearences }).reversed()[0]
                    val topRC = squad.data.sortedWith(compareBy { it.redcards }).reversed()[0]
                    val topYC = squad.data.sortedWith(compareBy { it.yellowcards }).reversed()[0]
                    uiThread {
                        view?.hideChartsLoading()
                        view?.setTopScorer(topScorer)
                        view?.setTopAssister(topAssist)
                        view?.setTopRC(topRC)
                        view?.setTopYC(topYC)
                        view?.setTopAppearance(ta)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    uiThread {
                        view?.hideCharts()
                    }
                }


            }
        }else view?.hideCharts()
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
        this.view = null
    }

    override fun attachView(view: BaseView) {
        this.view = view as SeasonContract.SeasonView
        loadFormGuideInfo()
        loadLeagueTable()
        loadTopCharts()
    }



}