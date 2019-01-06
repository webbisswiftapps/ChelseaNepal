package com.webbisswift.cfcn.v3.ui.screens.tabs.news

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NewsStreamModel(private val firebaseDBInstance: FirebaseDatabase): NewsStreamContract.NewsStreamModel {

    var newsStreamRef:DatabaseReference? = null
    var newsStreamListener:ValueEventListener? = null

    override fun subscribe(listener:ValueEventListener) {
        newsStreamListener = listener
        newsStreamRef = firebaseDBInstance.getReference("newsstream")
        newsStreamRef?.keepSynced(true)
        newsStreamRef?.addValueEventListener(listener)
    }



    override fun unsubscribe() {
        if(this.newsStreamRef != null && this.newsStreamListener != null){
            newsStreamRef?.removeEventListener(newsStreamListener!!)
        }
    }
}

