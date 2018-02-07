package com.webbisswift.cfcn.ui.screens.match_center

import android.content.Context
import android.hardware.usb.UsbEndpoint
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper

/**
 * Created by apple on 12/31/17.
 */

class MatchCenterModel(private val endpoint: String, private val firebaseDBInstance: FirebaseDatabase, private val context:Context){

    var liveMatchRef: DatabaseReference? = null
    var liveMatchListener: ValueEventListener? = null

    private  var settings:SettingsHelper?  = null



    fun subscribeToNextMatch(listener: ValueEventListener) {
        this.liveMatchRef = firebaseDBInstance.getReference("/v2/"+endpoint)
        liveMatchRef?.keepSynced(true)
        this.liveMatchListener = listener
        liveMatchRef?.addValueEventListener(this.liveMatchListener)
    }

    fun unsubscribeFromNextMatch() {
        if(this.liveMatchRef != null && this.liveMatchListener != null){
            liveMatchRef?.removeEventListener(liveMatchListener)
        }

    }


     fun getUserCountry(): String {

        if(settings == null)
            settings = SettingsHelper(context)

        return settings!!.getUserCurrentCountry()
    }

}