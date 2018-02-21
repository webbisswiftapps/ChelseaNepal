package com.webbisswift.cfcn.ui.screens.modal.compdetails.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.FirebaseDatabase
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.base.BaseFragment
import com.webbisswift.cfcn.base.BasePresenter
import com.webbisswift.cfcn.domain.model.v2.SMLeagueCharts
import kotlinx.android.synthetic.main.fragment_league_leaderboard.*
import org.jetbrains.anko.find

/**
 * Created by apple on 2/12/18.
 */


class LeaderboardFragment : BaseFragment(), LeaderboardContract.View{

    var presenter: LeaderboardContract.Presenter?= null

    var leagueId:Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_league_leaderboard, null, true)
        initView()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        loadAds()
    }

    private fun setListeners(){

    }


    /**
     * BaseFragment Implementation
     * */

    override fun initView() {
        if(leagueId!= null && leagueId!! > 0) {
            val model = LeaderboardModel(FirebaseDatabase.getInstance(), leagueId!!)
            this.presenter = LeaderboardPresenter(model)
        }
    }

    override fun getPresenter(): BasePresenter {
        return this.presenter as BasePresenter
    }


    override fun showAlert(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    private fun loadAds(){
        /*val adRequest = AdRequest.Builder()
                .build()
        adView.loadAd(adRequest) */
    }


    /**
     * Competition Overview View Implementation
     */


    override fun showLoading() {

    }

    override fun setTopGS(items: List<SMLeagueCharts.GoalItem>) {
        goalsLBHolder.removeAllViews()
        for(item in items){
            val view = LayoutInflater.from(context).inflate(R.layout.leaderboard_goals_item,goalsLBHolder, false)
            val playerName = view.findViewById<TextView>(R.id.playerName)
            val playerClub = view.findViewById<TextView>(R.id.playerTeam)
            val serialNo = view.findViewById<TextView>(R.id.serialNo)
            val goalCount = view.findViewById<TextView>(R.id.goalsCount)
            val penCount = view.findViewById<TextView>(R.id.penaltiesCount)
            val playerImg = view.findViewById<ImageView>(R.id.playerImg)

            playerName?.text = item.player.data.common_name
            playerClub?.text = item.team.data.name
            serialNo?.text = String.format("%d", item.position)
            goalCount.text = String.format("%d", item.goals)
            penCount.text = String.format("%d", item.penalty_goals)

            Glide.with(playerImg).load(item.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)

            goalsLBHolder.addView(view)
        }
    }

    override fun hideTopGSCard() {
        goalsLBCard?.visibility = View.GONE
    }

    override fun setTopCS(items: List<SMLeagueCharts.CardItem>) {
        cardsLBHolder.removeAllViews()
        for(item in items){
            val view = LayoutInflater.from(context).inflate(R.layout.leaderboard_goals_item,cardsLBHolder, false)
            val playerName = view.findViewById<TextView>(R.id.playerName)
            val playerClub = view.findViewById<TextView>(R.id.playerTeam)
            val serialNo = view.findViewById<TextView>(R.id.serialNo)
            val goalCount = view.findViewById<TextView>(R.id.goalsCount)
            val penCount = view.findViewById<TextView>(R.id.penaltiesCount)
            val playerImg = view.findViewById<ImageView>(R.id.playerImg)

            playerName?.text = item.player.data.common_name
            playerClub?.text = item.team.data.name
            serialNo?.text = String.format("%d", item.position)
            goalCount.text = String.format("%d", item.redcards)
            penCount.text = String.format("%d", item.yellowcards)

            Glide.with(playerImg).load(item.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)

            cardsLBHolder.addView(view)
        }
    }

    override fun hideTopCSCard() {
        cardsLBCard?.visibility = View.GONE
    }

    override fun setTopAS(items: List<SMLeagueCharts.AssistItem>) {
        assistsLBHolder.removeAllViews()
        for(item in items){
            val view = LayoutInflater.from(context).inflate(R.layout.leaderboard_assists_item,assistsLBHolder, false)
            val playerName = view.findViewById<TextView>(R.id.playerName)
            val playerClub = view.findViewById<TextView>(R.id.playerTeam)
            val serialNo = view.findViewById<TextView>(R.id.serialNo)
            val assistCount = view.findViewById<TextView>(R.id.assistCount)
            val playerImg = view.findViewById<ImageView>(R.id.playerImg)

            playerName?.text = item.player.data.common_name
            playerClub?.text = item.team.data.name
            serialNo?.text = String.format("%d", item.position)
            assistCount.text = String.format("%d", item.assists)

            Glide.with(playerImg).load(item.player.data.image_path).apply(RequestOptions.circleCropTransform()).into(playerImg)

            assistsLBHolder.addView(view)
        }
    }

    override fun hideTopASCard() {
        assistsLBCard?.visibility = View.GONE
    }

    override fun hideLoading() {

    }

    override fun showNoDataError() {
        assistsLBCard?.visibility = View.GONE
        goalsLBCard?.visibility = View.GONE
        cardsLBCard?.visibility = View.GONE
    }
}