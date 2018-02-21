package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.widget.TextView
import com.webbisswift.cfcn.R

/**
 * Created by apple on 12/4/17.
 */

internal class MainPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){

    private val mFragmentList:ArrayList<Fragment> = ArrayList()
    private val mCustomTitle:ArrayList<String> = ArrayList()


    fun addFragment(fragment:Fragment, title:String){
        mFragmentList.add(fragment)
        mCustomTitle.add(title)
    }




    override fun getItem(position: Int): Fragment {
       return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }



    fun setCustomViews(tabs:TabLayout){
        for ((index, value) in mCustomTitle.withIndex()) {
            val view = LayoutInflater.from(tabs.context).inflate(R.layout.tab_stats, tabs , false)
            (view as TextView)?.text = value
            tabs.getTabAt(index)?.customView = view
        }
    }


}