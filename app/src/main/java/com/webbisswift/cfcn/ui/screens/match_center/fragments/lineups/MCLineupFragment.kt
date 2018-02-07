package com.webbisswift.cfcn.ui.screens.match_center.fragments.lineups

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.LineupPlayer
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterModel
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.FontManager
import kotlinx.android.synthetic.main.layout_lineups.*

/**
 * Created by apple on 12/31/17.
 */


class MCLineupFragment : BaseFragment(), MCLineupContract.MCLineupView{

    var presenter: MCLineupContract.MCLineupPresenter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_lineups, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSegmentTheme()
        loadAds()
        setListeners()
    }


    override fun initView() {
        val ep  = (activity as MatchCenterUI).endpoint!!
        val model = MatchCenterModel(ep, FirebaseDatabase.getInstance(), context!!)
        this.presenter = MCLineupPresenter(model)
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    private fun setListeners(){
        lineupSwitchSegment?.setOnCheckedChangeListener { radioGroup, i ->
            when(radioGroup.checkedRadioButtonId){
                R.id.chelseaRB ->{
                  showChelseaLineup()
                }
                else ->{
                  showOpponentLineup()
                }
            }

        }
    }

    private fun loadAds(){
       /* val adRequest = AdRequest.Builder()
                .build()
        adView?.loadAd(adRequest)
        adViewLarge?.loadAd(adRequest)*/
    }

    private fun setupSegmentTheme(){
        lineupSwitchSegment?.setTintColor(Color.parseColor("#1565c0"))
        val fm = FontManager.getInstance(context)
        chelseaRB.setTypeface(fm.getFont(getString(R.string.font_montserrat_regular)))
        opponentRB.setTypeface(fm.getFont(getString(R.string.font_montserrat_regular)))
    }


    /**
     *  Lineup Display Methods
     *  */


    private fun showChelseaLineup(){
        if(lineupVS.currentView.id != R.id.cheLineupHolder) lineupVS.showPrevious()

    }

    private fun showOpponentLineup(){
        if(lineupVS.currentView.id != R.id.oppLineupHolder) lineupVS.showNext()
    }

    override fun showLineupsMain() {
        if(lineupMainSwitcher.currentView.id != R.id.lineupsMain) lineupMainSwitcher.showNext()
    }

    override fun showLineupError(error: String) {
        if(lineupMainSwitcher.currentView.id != R.id.lineupsErr) lineupMainSwitcher.showPrevious()
        lineupsErr.text = error
    }

    override fun setLineupChelsea(home: List<LineupPlayer>, subs: List<LineupPlayer>) {

        cheStarting.removeAllViews()
        cheSubs.removeAllViews()

        for(player in home){
            val nV = LayoutInflater.from(context).inflate(R.layout.item_squad_player, cheStarting, false)
            val numberV = nV.findViewById<TextView>(R.id.playerNumber)
            val nameV = nV.findViewById<TextView>(R.id.playerName)

            numberV.text = player.number
            nameV.text = Html.fromHtml(player.name)

            cheStarting.addView(nV)
        }

        for(player in subs){
            val nV = LayoutInflater.from(context).inflate(R.layout.item_squad_player, cheSubs, false)
            val numberV = nV.findViewById<TextView>(R.id.playerNumber)
            val nameV = nV.findViewById<TextView>(R.id.playerName)

            numberV.text = player.number
            nameV.text = player.name

            cheSubs.addView(nV)
        }

    }

    override fun setLineupOpponent(away: List<LineupPlayer>, subs: List<LineupPlayer>, name: String) {
        opponentRB.text = name

        oppStarting.removeAllViews()
        oppSubs.removeAllViews()

        for(player in away){
            val nV = LayoutInflater.from(context).inflate(R.layout.item_squad_player, oppStarting, false)
            val numberV = nV.findViewById<TextView>(R.id.playerNumber)
            val nameV = nV.findViewById<TextView>(R.id.playerName)

            numberV.text = player.number
            nameV.text = player.name

            oppStarting.addView(nV)
        }

        for(player in subs){
            val nV = LayoutInflater.from(context).inflate(R.layout.item_squad_player, oppSubs, false)
            val numberV = nV.findViewById<TextView>(R.id.playerNumber)
            val nameV = nV.findViewById<TextView>(R.id.playerName)

            numberV.text = player.number
            nameV.text = player.name

            oppSubs.addView(nV)
        }
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