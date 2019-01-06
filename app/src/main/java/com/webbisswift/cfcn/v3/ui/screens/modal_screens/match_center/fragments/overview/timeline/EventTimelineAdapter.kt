package com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.fragments.overview.timeline

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.github.vipulasri.timelineview.TimelineView
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.model.v2.SMEvents
import kotlinx.android.synthetic.main.v3_layout_match_event_timeline_item_home.view.*

class EventTimelineAdapter(private val events:List<SMEvents.EventData>, private val homeTeamId:String) : RecyclerView.Adapter<EventTimelineAdapter.EventViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): EventViewHolder {
        val  layoutInflater = LayoutInflater.from(p0.context)
        return EventViewHolder(layoutInflater.inflate(R.layout.v3_layout_match_event_timeline_item_home, p0, false), viewType)
    }

    override fun getItemCount(): Int = events.size


    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }


    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        val event = events[position]


        if(event?.extra_minute != null && event?.extra_minute > 0)
            holder.itemView.eventTime?.text = String.format("%d+%d'", event.minute, event.extra_minute)
        else holder.itemView.eventTime?.text = String.format("%d'", event?.minute)

        when(event.type){
            "goal"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.goal)
                holder.itemView.eventPlayer?.text = String.format("GOAL! %s ",event.player_name)
                val assist = if(!event.related_player_name.isNullOrBlank()) "Assist: "+event.related_player_name else ""
                val extra = assist + " "+event.result
                holder.itemView.eventExtra?.text = extra
            }

            "penalty"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.goal)
                holder.itemView.eventPlayer?.text = String.format("GOAL(Pen)! %s ",event.player_name)
                val extra = event.result
                holder.itemView.eventExtra?.text = extra
            }

            "substitution"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.sub)
                holder.itemView.eventPlayer?.text = String.format("Substitution: %s ",event.player_name)
                holder.itemView.eventExtra?.text = "comes in for  "+event.related_player_name
            }

            "yellowcard"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.yellow_card)
                holder.itemView.eventPlayer?.text = String.format("Yellow Card to %s ", event.player_name)
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                holder.itemView.eventExtra?.text = extra
            }

            "redcard"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.yellow_card)
                holder.itemView.eventPlayer?.text = String.format("%s ", event.player_name+" sent off")
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                holder.itemView.eventExtra?.text = extra
            }

            "yellowred"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.red_card)
                holder.itemView.eventPlayer?.text = String.format("%s ", event.player_name+" receives second yellow")
                val extra = if(!event.reason.isNullOrBlank()) "for  "+event.reason else ""
                holder.itemView.eventExtra?.text = extra
            }

            "own-goal"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.goal)
                holder.itemView.eventPlayer?.text = String.format("OWN GOAL! by %s ", event.player_name)
                val extra = event.result
                holder.itemView.eventExtra?.text = extra
            }

            "missed_penalty"->{
                holder.itemView.eventTypeImg?.setImageResource(R.drawable.ic_miss)
                holder.itemView.eventPlayer?.text = String.format("%s ", event.player_name+" missed penalty")
                holder.itemView.eventExtra?.text = ""
            }



            else ->{
                holder.itemView.eventTypeImg?.visibility = View.INVISIBLE
            }
        }


    }



    inner class EventViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val timeline = itemView.matchEventsTimeline

        init {
            timeline.initLine(viewType)
        }
    }

}