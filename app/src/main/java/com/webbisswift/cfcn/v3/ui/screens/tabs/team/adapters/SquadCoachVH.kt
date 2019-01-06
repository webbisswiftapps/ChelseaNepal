package com.webbisswift.cfcn.v3.ui.screens.tabs.team.adapters

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.v3_team_coach_card.view.*

/**
 * Created by biswas on 21/12/2017.
 */

class SquadCoachVH(private val view: View):SquadViewHolder(view){




    override fun setItem(item: SquadItem) {
        if(item.coach != null) {
            view.coach.text = item.coach.data.firstname+" "+item.coach.data.lastname
            view.coachHome.text = item.coach.data.nationality

            if(!item.coach.data.image_path.isNullOrEmpty()){
                Glide.with(view.coachImg).load(item.coach.data.image_path).apply(RequestOptions.circleCropTransform()).into(view.coachImg)

            }

        }
    }
}