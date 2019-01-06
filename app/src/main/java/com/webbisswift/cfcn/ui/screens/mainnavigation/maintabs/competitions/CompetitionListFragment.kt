package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMPlayingLeague
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.adapter.CompetitionsAdapter
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.compdetails.CompetitionDetailsUI
import kotlinx.android.synthetic.main.layout_competitions_list.*


/**
 * Created by apple on 2/11/18.
 */

class CompetitionListFragment : Fragment(), CompetitionsAdapter.CompetitionClickListener{

        companion object {

            fun makeFragment(subtab:Int): CompetitionListFragment {
                val frag = CompetitionListFragment()
                frag.page = subtab
                return frag
            }
        }


        var page:Int = 0

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_competitions, null, true)
            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initFragment()
        }

        private fun initFragment(){
            setupRecyclerView()
            loadCompetitions()
        }


    private lateinit var compAdapter:CompetitionsAdapter
        private fun setupRecyclerView(){
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            competitionList.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(competitionList.context, LinearLayoutManager.VERTICAL)
            competitionList.addItemDecoration(dividerItemDecoration)

            compAdapter = CompetitionsAdapter(this.context)
            compAdapter.clickListener = this
            competitionList.adapter = compAdapter
        }

        private fun loadCompetitions(){
            showCompetitionLoading()
            val ref = FirebaseDatabase.getInstance().getReference("v2/leagues/playing")
            ref.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    hideCompetitionLoading()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val t = object : GenericTypeIndicator<List<@kotlin.jvm.JvmSuppressWildcards SMPlayingLeague>>() {}
                    val leagues: List<SMPlayingLeague>? = snapshot.getValue(t)
                    showLeagues(leagues)

                }
            })
        }


        private fun showCompetitionLoading(){
            if(competitionSwitcher?.currentView?.id != R.id.competitionListProgress)
                competitionSwitcher.showPrevious()
        }

        private fun hideCompetitionLoading(){
            if(competitionSwitcher?.currentView?.id == R.id.competitionListProgress)
                competitionSwitcher.showNext()
        }


        private fun showLeagues(leagues:List<SMPlayingLeague>?){
            if(leagues != null && leagues.isNotEmpty()) {
                val withAd = ArrayList<SMPlayingLeague>()
                withAd.addAll(leagues.sortedWith(compareBy { it.priority }))
                val phantomLeague = SMPlayingLeague()
                phantomLeague.isIs_ad = true
                withAd.add(phantomLeague)

                this.compAdapter.addLeagues(withAd)
                hideCompetitionLoading()

            }
        }

    override fun onClickCompetition(competition: SMPlayingLeague) {
        //show competition details
        val ctx = context
        if(ctx != null) {
            val intent = CompetitionDetailsUI.getIntent(ctx, competition.id, competition.name)
            ctx.startActivity(intent)
        }
    }
}