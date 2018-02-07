package com.webbisswift.cfcn.ui.screens.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.home.fragments.overview.HomeFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.matches.MatchesFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.news.NewsFragment
import com.webbisswift.cfcn.ui.screens.home.fragments.season.SeasonFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_coordinator.*
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.webbisswift.cfcn.ui.screens.about_us.AboutUsUI
import com.webbisswift.cfcn.ui.screens.admin.AdminActivity
import com.webbisswift.cfcn.ui.screens.match_center.MatchCenterUI
import com.webbisswift.cfcn.ui.screens.settings.SettingsActivity
import com.webbisswift.cfcn.ui.screens.team.TeamInfoActivity
import com.webbisswift.cfcn.ui.screens.webview.WebViewActivity


/**
 * Created by apple on 12/3/17.
 */

class MainActivity : AppCompatActivity(){


    val kPlayServicesInstallReqCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabs()
        setupDrawer()

        checkPlayServices()
        if(isFirstCopyright()){
            showDisclaimer()
            setFirstCopyright()
        }

    }




    fun setupTabs(){
        val adapter = MainPagerAdapter(supportFragmentManager)

        adapter.addFragment(HomeFragment(), R.layout.tab_overview)
        adapter.addFragment(NewsFragment(), R.layout.tab_news)
        adapter.addFragment(SeasonFragment(), R.layout.tab_season)
        adapter.addFragment(MatchesFragment(), R.layout.tab_fixtures)


        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        adapter.setCustomViews(tabs)

        val notificationURL:String? = intent.getStringExtra("NOTIFICATION_URL")
        if(notificationURL != null && notificationURL.isNotBlank()){
            switchToNewsTabAndShowArticle(notificationURL)
        }
    }

    fun setupDrawer(){

        hamburgerMenuBtn.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        navigation_drawer.setNavigationItemSelectedListener { item: MenuItem ->  
            when(item.itemId){
                R.id.nav_home-> toHome()
                R.id.nav_players -> goToPlayers()
                R.id.nav_disclaimer -> showDisclaimer()
                R.id.nav_about_us -> toAboutUs()
                R.id.nav_settings -> goToSettings()
                else ->  true
            }
        }

        logo.setOnLongClickListener{
            startAdminActivity()
        }
    }

    private fun startAdminActivity():Boolean{
        val i = Intent(this, AdminActivity::class.java)
        startActivity(i)
        return true
    }

    private fun switchToNewsTabAndShowArticle(url:String){
        viewPager.setCurrentItem(1, false)
        val i = Intent(this, WebViewActivity::class.java)
        i.putExtra("URL", url)

        if (url.contains("youtube")) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                startActivity(i)
            }
        } else startActivity(i)
    }


    fun toHome():Boolean{
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun goToSettings():Boolean{
        val i = Intent(this, SettingsActivity::class.java)
        startActivity(i)
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun toAboutUs():Boolean{
        val i = Intent(this,AboutUsUI::class.java)
        startActivity(i)
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun showDisclaimer():Boolean{
        val dBuilder = AlertDialog.Builder(this)
        dBuilder.setTitle("Copyright & Disclaimer")
        dBuilder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_disclaimer, null, false))
        dBuilder.setPositiveButton("Ok", null)
        dBuilder.create().show()
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }


    fun toSeasonTab(){
        viewPager.currentItem = 2
    }

    fun goToPlayers():Boolean{
        val lmIntent = Intent(this, TeamInfoActivity::class.java)
        startActivity(lmIntent)
        drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }


    fun toMatchCenter(endpoint:String){
        val mcIntent = Intent(this, MatchCenterUI::class.java)
        mcIntent.putExtra("ENDPOINT", endpoint)
        startActivity(mcIntent)
    }


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }*/


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun isFirstCopyright():Boolean{

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return sp.getBoolean("IS_FIRST_CR", true);
    }

    private fun setFirstCopyright(){
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("IS_FIRST_CR", false)
        editor.apply()
    }



    fun checkPlayServices(){
        val playServicesChecker = GoogleApiAvailability.getInstance()
        val googlePlayAvailability = playServicesChecker.isGooglePlayServicesAvailable(this)

        when(googlePlayAvailability){
            ConnectionResult.SERVICE_MISSING,
            ConnectionResult.SERVICE_DISABLED,
            ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED,
            ConnectionResult.SERVICE_MISSING_PERMISSION,
            ConnectionResult.SERVICE_INVALID ->{
                Log.d("PlayServicesCheck", "Play services not available or missing or disabled or requires update. Showing relevant dialog.")
                val errorDialog = playServicesChecker.getErrorDialog(this, googlePlayAvailability, kPlayServicesInstallReqCode)
                errorDialog.show()
            }

        }
    }


}