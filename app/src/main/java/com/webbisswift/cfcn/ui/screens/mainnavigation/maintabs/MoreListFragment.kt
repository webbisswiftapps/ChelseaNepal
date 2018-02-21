package com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.mainnavigation.MainNavigationActivity
import com.webbisswift.cfcn.ui.screens.modal.AboutUsUI
import com.webbisswift.cfcn.ui.screens.modal.admin.AdminActivity
import com.webbisswift.cfcn.ui.screens.modal.settings.SettingsActivity
import kotlinx.android.synthetic.main.more_list_fragment.*
import android.content.ActivityNotFoundException
import android.net.Uri


/**
 * Created by apple on 2/16/18.
 */

class MoreListFragment:Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.more_fragment, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        settingsLink.setOnClickListener{ goToSettings()}
        shareApp.setOnClickListener{
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, "CFCN")
                var sAux = "\nThe ultimate Chelsea Fan App\n\n"
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.webbisswift.cfcn&hl=en"
                i.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(i, "Choose One"))
            } catch (e: Exception) {
                //e.toString();
                e.printStackTrace()
            }

        }
        rateApp.setOnClickListener{

            val uri = Uri.parse("market://details?id=" + context?.getPackageName())
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context?.getPackageName())))
            }

        }
        aboutUs.setOnClickListener { toAboutUs() }
        disclaimer.setOnClickListener { (activity as MainNavigationActivity).showDisclaimer() }
        aboutUs.setOnLongClickListener { startAdminActivity() }
    }

    /**
     * Other Methods
     */

    private fun startAdminActivity():Boolean{
        val i = Intent(context, AdminActivity::class.java)
        startActivity(i)
        return true
    }

    fun goToSettings():Boolean{
        val i = Intent(context, SettingsActivity::class.java)
        startActivity(i)
        return true
    }

    fun toAboutUs():Boolean{
        val i = Intent(context, AboutUsUI::class.java)
        startActivity(i)
        return true
    }

}