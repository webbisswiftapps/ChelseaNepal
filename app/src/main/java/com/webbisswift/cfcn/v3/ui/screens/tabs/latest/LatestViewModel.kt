package com.webbisswift.cfcn.v3.ui.screens.tabs.latest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.*
import com.webbisswift.cfcn.domain.model.v2.NewsStreamItem
import com.webbisswift.cfcn.domain.model.v2.SMMatch
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague
import com.webbisswift.cfcn.domain.model.v2.SMSquad

class LatestViewModel : ViewModel(){

    var nextMatch: MutableLiveData<SMMatch> = MutableLiveData<SMMatch>()
    var nextMatchRef:DatabaseReference? = null
    var nextMatchListener:ValueEventListener? = null

    var lastMatch: MutableLiveData<SMMatch> = MutableLiveData<SMMatch>()
    var lastMatchRef:DatabaseReference? = null
    var lastMatchListener:ValueEventListener? = null

    var topNews: MutableLiveData<List<NewsStreamItem>> = MutableLiveData<List<NewsStreamItem>>()
    var topNewsReference:DatabaseReference? = null
    var topNewsListener:ValueEventListener? = null


    var playingLeagues:MutableLiveData<List<SMPlayingLeague>> = MutableLiveData<List<SMPlayingLeague>>()
    var playingLeaguesReference:DatabaseReference? = null
    var playingLeaguesListener:ValueEventListener? = null


    var squad:MutableLiveData<SMSquad> = MutableLiveData<SMSquad>()
    var squadReference:DatabaseReference? = null
    var squadListener:ValueEventListener? = null

    fun getNextMatch() : LiveData<SMMatch>{

        if(nextMatch.value == null){
            nextMatchRef = FirebaseDatabase.getInstance().getReference("v2/next-match")
            nextMatchRef?.keepSynced(true)
            nextMatchListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){

                        try {
                            val nextMatchInfo = snapshot.getValue<SMMatch>(SMMatch::class.java)
                            nextMatch.postValue(nextMatchInfo)
                        }catch (e:java.lang.Exception){
                            nextMatch.postValue(null)
                            e.printStackTrace()
                        }
                    }else nextMatch.postValue(null)
                }
            }

            nextMatchRef?.addValueEventListener(nextMatchListener!!)
            nextMatch.postValue(null)
        }

        return nextMatch

    }


    fun getLastMatch() : LiveData<SMMatch>{
        if(lastMatch.value == null){
            lastMatchRef = FirebaseDatabase.getInstance().getReference("v2/last-match")
            lastMatchRef?.keepSynced(true)
            lastMatchListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){

                        try {
                            val nextMatchInfo = snapshot.getValue<SMMatch>(SMMatch::class.java)
                            lastMatch.postValue(nextMatchInfo)
                        }catch (e:java.lang.Exception){
                            lastMatch.postValue(null)
                            e.printStackTrace()
                        }
                    }else{
                        lastMatch.postValue(null)
                    }
                }
            }
            lastMatchRef?.addValueEventListener(lastMatchListener!!)
            lastMatch.postValue(null)
        }

        return lastMatch
    }


    fun getPlayingLeagues():LiveData<List<SMPlayingLeague>>{
        if(playingLeagues.value == null){
            playingLeaguesReference = FirebaseDatabase.getInstance().getReference("v2/leagues/playing")
            playingLeaguesReference?.keepSynced(true)
            playingLeaguesListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        try {
                            val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMPlayingLeague>>() {}
                            val leagues: List<SMPlayingLeague>? = snapshot.getValue(t)
                            playingLeagues.postValue(leagues)
                        }catch(e:Exception){
                            e.printStackTrace()
                            playingLeagues.postValue(null)
                        }
                    }else playingLeagues.postValue(null)
                }
            }
            playingLeaguesReference?.addValueEventListener(playingLeaguesListener!!)
            playingLeagues.postValue(null)
        }

        return playingLeagues
    }

    fun getTopNews() : LiveData<List<NewsStreamItem>>{
        if(topNews.value == null){
            topNewsReference = FirebaseDatabase.getInstance().getReference("newsstream")
            topNewsReference?.keepSynced(true)
            topNewsListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        try {
                            val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards NewsStreamItem>>() {}
                            val formItems: List<NewsStreamItem>? = snapshot.getValue(t)
                            if(formItems != null){
                                val items = formItems.filter { it.type.contentEquals("news_article") && it.isIs_highlighted }.take(10)
                                topNews.postValue(items)
                            }
                        }catch(e:Exception){
                            e.printStackTrace()
                            topNews.postValue(null)
                        }
                    }else topNews.postValue(null)
                }
            }
            topNewsReference?.addValueEventListener(topNewsListener!!)
            topNews.postValue(null)
        }

        return topNews
    }



    fun getSquad() : LiveData<SMSquad>{
        if(squad.value == null){
            squadReference = FirebaseDatabase.getInstance().getReference("v2/team/squad")
            squadReference?.keepSynced(true)
            squadListener = object:ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        try {
                            val sqd = snapshot.getValue<SMSquad>(SMSquad::class.java)
                            squad.postValue(sqd)
                        }catch(e:Exception){
                            e.printStackTrace()
                            squad.postValue(null)
                        }
                    }else squad.postValue(null)
                }
            }
            squadReference?.addValueEventListener(squadListener!!)
            squad.postValue(null)
        }

        return squad
    }


    override fun onCleared() {
        super.onCleared()
        if(lastMatchListener != null) lastMatchRef?.removeEventListener(lastMatchListener!!)
        if(nextMatchListener != null) nextMatchRef?.removeEventListener(nextMatchListener!!)
        if(topNewsListener != null) topNewsReference?.removeEventListener(topNewsListener!!)
        if(playingLeaguesListener != null) playingLeaguesReference?.removeEventListener(playingLeaguesListener!!)
        if(squadListener != null) squadReference?.removeEventListener(squadListener!!)

    }



}