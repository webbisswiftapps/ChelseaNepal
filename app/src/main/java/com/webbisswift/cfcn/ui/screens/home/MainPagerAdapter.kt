package com.webbisswift.cfcn.ui.screens.home

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by apple on 12/4/17.
 */

internal class MainPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){

    private val mFragmentList:ArrayList<Fragment> = ArrayList()
    private val mCustomViewList:ArrayList<Int> = ArrayList()


    fun addFragment(fragment:Fragment, tabViewId:Int){
        mFragmentList.add(fragment)
        mCustomViewList.add(tabViewId)
    }
    override fun getItem(position: Int): Fragment {
       return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun getCustomViewIdAt(position:Int):Int{
        return mCustomViewList[position]
    }

    fun setCustomViews(tabs:TabLayout){
        for ((index, value) in mCustomViewList.withIndex()) {
            tabs.getTabAt(index)?.setCustomView(value)
        }
    }


}