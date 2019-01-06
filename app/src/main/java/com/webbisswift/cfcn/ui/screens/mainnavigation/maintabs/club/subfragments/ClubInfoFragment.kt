package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.subfragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.*
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMUefaRanking
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.newsview.WebViewActivity
import kotlinx.android.synthetic.main.ad_card_small_overview.*
import kotlinx.android.synthetic.main.club_info_fragment.*

/**
 * Created by apple on 2/16/18.
 */

class ClubInfoFragment: Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.club_info_fragment, null , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        loadUEFARanking()
        setListeners()
        loadAds()
    }

    private fun setListeners(){
        clubWebsiteLink.setOnClickListener{
            val i = Intent(context, WebViewActivity::class.java)
            i.putExtra("URL", "https://www.chelseafc.com")
            startActivity(i)
        }
    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
    }

    private fun loadUEFARanking(){
        uefaRankingCard?.visibility = View.GONE
        val ref = FirebaseDatabase.getInstance().getReference("v2/team/uefaranking")
        ref.keepSynced(true)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                uefaRankingCard?.visibility = View.GONE
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val ranking: SMUefaRanking? = snapshot.getValue<SMUefaRanking>(SMUefaRanking::class.java)
                    presentUefaRanking(ranking)
                }catch (e:Exception){
                    e.printStackTrace()
                    uefaRankingCard.visibility = View.GONE
                }
            }
        })

    }


    private fun presentUefaRanking(ranking:SMUefaRanking?){
        if(ranking != null){
            uefaPos?.text = String.format("%d", ranking.data.position)
            uefaPts?.text = String.format("%d", ranking.data.points)
            uefaCoeff?.text = String.format("%d", ranking.data.coeffiecient)

            uefaRankingCard?.visibility = View.VISIBLE

        }else uefaRankingCard?.visibility = View.GONE
    }

}