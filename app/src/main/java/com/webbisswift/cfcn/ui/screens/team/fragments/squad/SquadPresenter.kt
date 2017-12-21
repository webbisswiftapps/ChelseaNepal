package com.webbisswift.cfcn.ui.screens.team.fragments.squad

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.Match
import com.webbisswift.cfcn.domain.model.SquadPlayer
import com.webbisswift.cfcn.domain.model.TeamInfo
import com.webbisswift.cfcn.ui.screens.home.fragments.matches.MatchesContract
import com.webbisswift.cfcn.ui.screens.team.TeamInfoModel

/**
 * Created by biswas on 21/12/2017.
 */

class SquadPresenter(val model:TeamInfoModel) : SquadContract.SquadPresenter{

    var view: SquadContract.SquadView? = null

    override fun loadCurrentSquad() {
        this.view?.showSquadLoading()
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val teamInfo = dataSnapshot.getValue<TeamInfo>(TeamInfo::class.java)
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
        this.model.subscribeToTeamInfo(nextMatchListener)
    }

    fun presentTeamInfo(teamInfo: TeamInfo?){
        view?.hideSquadLoading()
        if(teamInfo != null){
            val squad = teamInfo.squad
            if(squad != null){

                //1. add coach
                view?.clearList()
                view?.addCoach(teamInfo.coach_name)

                //2. add Players by group
                var lastGroup = "Goalkeeper"
                var group:ArrayList<SquadPlayer> = ArrayList()
                for(player in squad){
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
        }else view?.showNoSquadError()
    }

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as SquadContract.SquadView
        loadCurrentSquad()
    }

    override fun detachView() {
        this.model.unsubscribeFromTeamInfo()
    }
}