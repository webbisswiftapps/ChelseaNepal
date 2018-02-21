package com.webbisswift.cfcn.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.mainnavigation.MainNavigationActivity
import kotlinx.android.synthetic.main.layout_country_selector.*

/**
 * Created by biswas on 03/01/2018.
 */

class CountrySelectorActivity : AppCompatActivity(){

    lateinit var settings:SettingsHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_country_selector)
        settings = SettingsHelper(this);
        setListeners()
    }


    private fun setListeners(){
        continueBtn.setOnClickListener({

            val selected = countrySpinner.selectedItem as? String
            if(selected != null && selected.isNotBlank() && !selected.contentEquals("Select a Region")){
                settings.setUserCurrentCountry(selected)
            }

            if(dontShowCB.isChecked) settings.setDontAskCountryAgain()


            // now move to Main Activity
            moveToHome()

        })
    }

    private fun moveToHome(){

            val i = Intent(this, MainNavigationActivity::class.java)
            startActivity(i)
            finish()


    }





}