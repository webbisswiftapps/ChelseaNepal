package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.subfragments.matches

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by apple on 12/7/17.
 */


class MatchesPresenter(val model: MatchesModel): MatchesContract.MatchesPresenter {

    var view: MatchesContract.MatchesView? = null
    val matches:ArrayList<SMMatch> = ArrayList()
    var fixtures:List<SMMatch>? = null
    var results:List<SMMatch>? = null
    var fixturesLoaded = false
    var resultsLoaded = false


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/
    override fun loadFixtures() {
        this.view?.showLoading()
        fixturesLoaded = false
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doAsync {

                    try {
                        val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMMatch>>() {}
                        val matches: List<SMMatch>? = dataSnapshot.getValue(t)
                        uiThread {
                            presentFixtures(matches)
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToFixtures(nextMatchListener)
    }

    override fun loadResults() {
        this.view?.showLoading()
        resultsLoaded = false
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doAsync {
                    try {
                        val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMMatch>>() {}
                        val matches: List<SMMatch>? = dataSnapshot.getValue(t)?.reversed()
                        uiThread {
                            presentResults(matches)
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToResults(nextMatchListener)
    }

    /**
     * Presentation Methods
     **/
    fun presentFixtures(matches:List<SMMatch>?){
        fixtures = matches
        fixturesLoaded = true
         checkLoaded()
    }

    fun presentResults(matches:List<SMMatch>?){
        results = matches
        resultsLoaded = true
        checkLoaded()
    }


    fun checkLoaded(){
        if(fixturesLoaded && resultsLoaded){
            view?.hideLoading()
            val array = ArrayList<SMMatch>()
            val adItem = SMMatch()
            adItem.isAd = true

            if(results!=null && results!!.isNotEmpty()){
                array.addAll(results!!)
                if(array.size > 3){
                    array.add(2, adItem)
                }else{
                    array.add(adItem)
                }
            }else{
                array.add(adItem)
            }

            matches.clear()
            matches.addAll(array)
            matches.addAll(fixtures!!)

            view?.addMatches(matches, array.size - 1)
        }
    }


    /**
     * LifeCycle Methods
     */

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun detachView() {
        this.view = null
    }

    override fun attachView(view: BaseView) {
        this.view = view as MatchesContract.MatchesView
        loadResults()
        loadFixtures()

    }

}



