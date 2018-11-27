package com.webbisswift.cfcn.v3.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.v3.ui.screens.mainnav.MainNavigationActivity
import kotlinx.android.synthetic.main.v3_layout_country_selector.*

/**
 * Created by biswas on 03/01/2018.
 */

class CountrySelectorActivity : AppCompatActivity(){

    lateinit var settings:SettingsHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.v3_layout_country_selector)
        settings = SettingsHelper(this);
        setListeners()
    }


    private fun setListeners(){
        btn_continue.setOnClickListener{

            val selected = regionSelectionText.text.toString()
            if(!selected.isNullOrEmpty()){
                settings.setUserCurrentCountry(selected)
            }

            // now move to Main Activity
            moveToHome()

        }


        btn_skip.setOnClickListener { moveToHome() }

        regionSelectionText.setOnClickListener {

            val builder = AlertDialog.Builder(this@CountrySelectorActivity)
            builder.setTitle(getString(R.string.choose_region))
            val items = resources.getStringArray(R.array.countries)

            builder.setSingleChoiceItems(items, -1) { dialogInterface, i ->
                regionSelectionText.setText(items[i])
                dialogInterface.dismiss()
            }

            builder.create().show()

        }
    }

    private fun moveToHome(){

        //  val i = Intent(this, MainNavigationActivity::class.java)
        val i = Intent(this, MainNavigationActivity::class.java)
        startActivity(i)
        finish()


    }





}