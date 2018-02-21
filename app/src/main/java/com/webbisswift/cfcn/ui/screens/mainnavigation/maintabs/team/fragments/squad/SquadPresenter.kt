package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.fragments.squad

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMCoach
import com.webbisswift.cfcn.domain.model.v2.SMSquad

/**
 * Created by biswas on 21/12/2017.
 */

class SquadPresenter(val model:SquadContract.SquadModel) : SquadContract.SquadPresenter{

    var view: SquadContract.SquadView? = null


    override fun loadCurrentSquad() {
        this.view?.showSquadLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<SMSquad>(SMSquad::class.java)
                    presentTeamInfo(teamInfo)
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoSquadError()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToSquad(nextMatchListener)
    }

    override fun loadCoach() {
        this.view?.showSquadLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<SMCoach>(SMCoach::class.java)
                    presentCoach(teamInfo)
                    loadCurrentSquad()
                }catch(e:Exception){
                    e.printStackTrace()
                    view?.showNoSquadError()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        this.model.subscribeToCoach(nextMatchListener)
    }

    fun presentTeamInfo(squad: SMSquad?){
        view?.hideSquadLoading()
        if(squad != null && squad.data.size > 0){
            //2. add Players by group
            var lastGroup = "Goalkeeper"
            var group:ArrayList<SMSquad.SMSquadItem> = ArrayList()
            for(player in squad.data.sortedWith(compareBy { it.position_id })){
                if(player == null) continue
                if(player.positionName.contentEquals(lastGroup)){
                    group.add(player)
                }else{
                    view?.addSquadGroup(lastGroup, group)
                    group.clear()
                    lastGroup = player.positionName
                    group.add(player)
                }
            }
            view?.addSquadGroup(lastGroup , group)
        }else view?.showNoSquadError()
    }

    fun presentCoach(coach:SMCoach?){
        if(coach!= null){
            view?.addCoach(coach.data.fullname)
        }
    }

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as SquadContract.SquadView
        loadCoach()
    }

    override fun detachView() {
        this.model.unsubscribe()
        this.view = null
    }
}