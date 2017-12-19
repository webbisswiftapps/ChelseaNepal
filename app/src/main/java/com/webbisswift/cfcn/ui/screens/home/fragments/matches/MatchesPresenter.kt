package com.webbisswift.cfcn.ui.screens.home.fragments.matches

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match

/**
 * Created by apple on 12/7/17.
 */


class MatchesPresenter(val model: MatchesModel): MatchesContract.MatchesPresenter {

    var view: MatchesContract.MatchesView? = null


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/
    override fun loadFixtures() {
        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val t = object: GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards Match>>(){}
                val matches:List<Match>? = dataSnapshot.getValue(t)
                presentFixtures(matches)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToFixtures(nextMatchListener)
    }

    override fun loadResults() {
        this.view?.showLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {



                try {
                    val t = object: GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards Match>>(){}
                    val matches:List<Match>? = dataSnapshot.getValue(t)
                    presentResults(matches)
                }catch (e:Exception){}
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
    fun presentFixtures(matches:List<Match>?){

        if(matches!=null && matches?.size > 0){
            view?.hideLoading()
            view?.addFixtures(matches)
        }

    }

    fun presentResults(matches:List<Match>?){

        if(matches!=null && matches?.size > 0){
            view?.hideLoading()
            view?.addResults(matches)
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
    }

    override fun attachView(view: BaseView) {
        this.view = view as MatchesContract.MatchesView
        loadResults()
        loadFixtures()

    }

}



