package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.lineups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMLineup
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterUI
import kotlinx.android.synthetic.main.v3_layout_lineups.*

/**
 * Created by apple on 12/31/17.
 */


class MCLineupFragment : BaseFragment(), MCLineupContract.MCLineupView{

    var presenter: MCLineupContract.MCLineupPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.v3_layout_lineups, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAds()
    }


    override fun initView() {
        val ep  = (activity as MatchCenterUI).endpoint!!
        val model = MatchCenterModel(ep, FirebaseDatabase.getInstance(), context!!)
        this.presenter = MCLineupPresenter(model)
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }




    private fun loadAds(){
       /* val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
        adViewLarge?.loadAd(adRequest)*/
    }




    /**
     *  Lineup Display Methods
     *  */



    override fun showLineupsMain() {
        if(lineupMainSwitcher.currentView.id != R.id.lineupsMain) lineupMainSwitcher.showNext()
    }

    override fun showLineupError(error: String) {
        if(lineupMainSwitcher.currentView.id != R.id.lineupsErr) lineupMainSwitcher.showPrevious()
        lineupsErr.text = error
    }



    override fun setLineupHome(home: List<SMLineup.SMLineupPlayer>?, subs: List<SMLineup.SMLineupPlayer>?,name:String, formation: String?) {
        homeStarting?.removeAllViews()
        homeBench?.removeAllViews()
        homeFormation?.text = formation

        if(home != null) {
            for (player in home) {
                val nV = LayoutInflater.from(context).inflate(R.layout.v3_item_lineup_player, homeStarting, false)
                val numberV = nV.findViewById<TextView>(R.id.playerNumber)
                val nameV = nV.findViewById<TextView>(R.id.playerName)
                val playerImg = nV.findViewById<ImageView>(R.id.playerImg)
                val playerPos = nV.findViewById<TextView>(R.id.playerPos)

                playerPos?.text = player.position
                numberV.text = String.format("%d", player.number)
                nameV.text = player.player_name
                Glide.with(context!!).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
                homeStarting.addView(nV)
            }
        }

        if(subs != null) {
            for (player in subs) {
                val nV = LayoutInflater.from(context).inflate(R.layout.v3_item_lineup_player, homeStarting, false)
                val numberV = nV.findViewById<TextView>(R.id.playerNumber)
                val nameV = nV.findViewById<TextView>(R.id.playerName)
                val playerImg = nV.findViewById<ImageView>(R.id.playerImg)
                val playerPos = nV.findViewById<TextView>(R.id.playerPos)

                playerPos?.text = player.position
                numberV.text = String.format("%d", player.number)
                nameV.text = player.player_name
                Glide.with(context!!).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
                homeBench.addView(nV)
            }
            homeBench.visibility = View.VISIBLE
        }else homeBench.visibility = View.GONE

    }

    override fun setLineupAway(away: List<SMLineup.SMLineupPlayer>?, subs: List<SMLineup.SMLineupPlayer>?, name: String, formation: String?) {
        awayStarting?.removeAllViews()
        awayBench?.removeAllViews()
        awayFormation?.text = formation

        if(away != null) {
            for (player in away) {
                val nV = LayoutInflater.from(context).inflate(R.layout.v3_item_lineup_player, awayStarting, false)
                val numberV = nV.findViewById<TextView>(R.id.playerNumber)
                val nameV = nV.findViewById<TextView>(R.id.playerName)
                val playerImg = nV.findViewById<ImageView>(R.id.playerImg)
                val playerPos = nV.findViewById<TextView>(R.id.playerPos)

                playerPos?.text = player.position
                numberV.text = String.format("%d", player.number)
                nameV.text = player.player_name
                Glide.with(context!!).load(player.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)
                awayStarting.addView(nV)
            }
        }

        if(subs != null) {
            for (player in subs) {
                val nV = LayoutInflater.from(context).inflate(R.layout.v3_item_lineup_player, awayStarting, false)
                val numberV = nV.findViewById<TextView>(R.id.playerNumber)
                val nameV = nV.findViewById<TextView>(R.id.playerName)
                val playerImg = nV.findViewById<ImageView>(R.id.playerImg)
                val playerPos = nV.findViewById<TextView>(R.id.playerPos)

                playerPos?.text = player.position
                numberV.text = String.format("%d", player.number)
                nameV.text = player.player_name
                Glide.with(context!!).load(player?.player?.data?.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)

                awayBench.addView(nV)
            }
            awayBench.visibility = View.VISIBLE

        }else awayBench.visibility = View.GONE

    }



    /**
     * Base View Methods
     */
    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}