package com.webbisswift.cfcn.ui.screens.modal

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.newsview.WebViewActivity
import kotlinx.android.synthetic.main.layout_about_us.*

/**
 * Created by biswas on 24/12/2017.
 */

class AboutUsUI : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_about_us)
        initActivity()
    }


    private fun initActivity(){
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        joinGroupLink.setOnClickListener {
            openLink("https://www.facebook.com/groups/Chelseafanclubnepal/")
        }

        fbLink.setOnClickListener{
            openLink("https://www.facebook.com/biswasl/")
        }

        moreAppsLink.setOnClickListener{
            openLink("https://play.google.com/store/apps/developer?id=Webbisswift+Applications")
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> finish()
        }

        return false
    }


    private fun openLink(url:String){
        val i = Intent(this, WebViewActivity::class.java)
        i.putExtra("URL", url)

        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(i)
        }
    }


}
