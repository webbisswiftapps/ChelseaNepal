package com.webbisswift.cfcn.ui.screens.modal.match_center.fragments.lineups

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.base.BaseView
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMTeamShort
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by biswas on 01/01/2018.
 */


class MCLineupPresenter(val model: MatchCenterModel): MCLineupContract.MCLineupPresenter {

    var view: MCLineupContract.MCLineupView? = null


    /**
     * Non Lifecycle Methods : Load Info methods
     * **/


    override fun loadNextMatchInfo() {
        val nextMatchListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                doAsync {
                    try {
                        val lmInfo = dataSnapshot.getValue<SMMatch>(SMMatch::class.java)
                        uiThread {
                            presentNextMatchInfo(lmInfo)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        uiThread {
                            view?.showLineupError("Lineups not available.")
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
                view?.showLineupError("Lineups not available.")
            }
        }
        this.model.subscribeToNextMatch(nextMatchListener)


    }

    /**
     * Presentation Methods
     **/

    fun presentNextMatchInfo(match: SMMatch?) {


        if (match != null  ) {


            if(match.lineup != null && match.lineup.data.size > 0){
                val isChelseaHomeTeam = match.localteam_id == 18

                val home11 = match.lineup?.data?.filter {  it.team_id == match.localteam_id }
                val away11 = match.lineup?.data?.filter {  it.team_id == match.visitorteam_id }



                val homebench = match.bench?.data?.filter {  it.team_id == match.localteam_id }
                val awaybench = match.bench?.data?.filter {  it.team_id == match.visitorteam_id }

                var homeformation = "n/a"
                var awayformation = "n/a"

                if(match.formations != null){
                    homeformation = match.formations.localteam_formation
                    awayformation = match.formations.visitorteam_formation
                }

                if(isChelseaHomeTeam){
                    //chelsea is home team
                    view?.showLineupsMain()
                    view?.setLineupChelsea(home11, homebench, homeformation)
                    view?.setLineupOpponent(away11,awaybench, SMTeamShort.getTeamShort(match.visitorTeam?.data?.name), awayformation)
                }else{
                    //chelsea is away team
                    view?.showLineupsMain()
                    view?.setLineupChelsea(away11, awaybench, awayformation)
                    view?.setLineupOpponent(home11, homebench, SMTeamShort.getTeamShort(match.localTeam?.data?.name), homeformation)
                }

            }else view?.showLineupError("Lineups not available.")
        } else view?.showLineupError("Lineups not available.")
    }

    /**
     * LifeCycle Methods
     */

    override fun resume() {
    }

    override fun pause() {
    }


    override fun destroy() {
        this.model.unsubscribeFromNextMatch()
    }

    override fun detachView() {
    }

    override fun attachView(view: BaseView) {
        this.view = view as MCLineupContract.MCLineupView
        loadNextMatchInfo()
    }




}