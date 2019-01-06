package com.webbisswift.cfcn.v3.ui.screens.tabs.latest.fragments.competitions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.*
import com.webbisswift.cfcn.domain.model.v2.SMLeague
import com.webbisswift.cfcn.domain.model.v2.SMLeagueMatches
import com.webbisswift.cfcn.domain.model.v2.SMStandingItem

class CompViewModel : ViewModel(){

    var standings: MutableLiveData<List<SMStandingItem>> = MutableLiveData<List<SMStandingItem>>()
    var standingReference: DatabaseReference? = null
    var standingListener: ValueEventListener? = null

    var matches: MutableLiveData<SMLeagueMatches> = MutableLiveData<SMLeagueMatches>()
    var matchesReference: DatabaseReference? = null
    var matchesListener: ValueEventListener? = null


    fun getStandings(lg:Int) : LiveData<List<SMStandingItem>> {

        if(standings.value == null){
            standingReference = FirebaseDatabase.getInstance().getReference("v2/leagues/$lg/standings")
            standingReference?.keepSynced(true)
            standingListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        try {
                            val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMStandingItem>>() {}
                            val tableItems: List<SMStandingItem>? = snapshot.getValue(t)
                            standings.postValue(tableItems)
                        }catch(e:Exception){
                            standings.postValue(null)
                            e.printStackTrace()
                        }
                    }
                }
            }

            standingReference?.addValueEventListener(standingListener!!)
        }

        return standings
    }


    fun getMatches(lg:Int):LiveData<SMLeagueMatches>{
        if(matches.value == null){
            matchesReference = FirebaseDatabase.getInstance().getReference("v2/leagues/$lg/matches")
            matchesReference?.keepSynced(true)
            matchesListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        try {
                            val mat: SMLeagueMatches? = snapshot.getValue(SMLeagueMatches::class.java)
                            matches.postValue(mat)
                        }catch(e:Exception){
                            matches.postValue(null)
                            e.printStackTrace()
                        }
                    }
                }
            }
            matchesReference?.addValueEventListener(matchesListener!!)
        }
        return matches
    }



    override fun onCleared() {
        super.onCleared()
        if(standingListener != null) standingReference?.removeEventListener(standingListener!!)
        if(matchesListener != null) matchesReference?.removeEventListener(matchesListener!!)
    }

}