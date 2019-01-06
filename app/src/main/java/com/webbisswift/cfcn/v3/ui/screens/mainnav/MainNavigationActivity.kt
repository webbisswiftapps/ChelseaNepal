package com.webbisswift.cfcn.v3.ui.screens.mainnav

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.webbisswift.cfcn.R
import kotlinx.android.synthetic.main.v3_layout_main_navigation.*
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.kobakei.ratethisapp.RateThisApp
import com.webbisswift.cfcn.BuildConfig
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.MoreListFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.ClubFragment
import com.webbisswift.cfcn.v3.ui.screens.tabs.news.NewsStreamFragment
import com.webbisswift.cfcn.v3.ui.screens.modal_screens.match_center.MatchCenterUI
import com.webbisswift.cfcn.utils.NotificationUtils
import com.webbisswift.cfcn.v3.ui.screens.tabs.latest.LatestFragment
import com.webbisswift.cfcn.v3.ui.screens.tabs.team.TeamFragment


class MainNavigationActivity : AppCompatActivity(),AHBottomNavigation.OnTabSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener{




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.v3_layout_main_navigation)
        setupNavigation()
        checkPlayServices()
        if(isFirstCopyright()){
            showDisclaimer()
            setFirstCopyright()
        }

        //check if coming from notification
        //val action = intent.action

        if(intent.action != null){
            if(intent.action.contentEquals("OPEN_MATCH_CENTER") || intent.action.contentEquals("OPEN_MATCH_CENTER_LINEUPS")){
                this.toMatchCenter(intent.action)
            }
        }

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // Compatibility for Android Oreo. Setup Notification Channels
            val nUtils = NotificationUtils(this)
            nUtils.createChannels()
        }
        subscribeFirebaseTopics()

        /* Ask user for app rating if required */
        RateThisApp.showRateDialogIfNeeded(this)
    }

    private fun setupNavigation(){
        val latestItem = AHBottomNavigationItem(R.string.home, R.drawable.ic_bnav_clock, R.color.text_white)
        val compItem = AHBottomNavigationItem(R.string.tab_news, R.drawable.v3_rss_feed_icon, R.color.text_white)
        val teamItem = AHBottomNavigationItem(R.string.players, R.drawable.v3_ic_team, R.color.text_white)
        val clubItem = AHBottomNavigationItem(R.string.team, R.drawable.ic_bnav_club, R.color.text_white)
        val moreItem = AHBottomNavigationItem(R.string.more, R.drawable.ic_bnav_more, R.color.text_white)

        bottom_navigation.addItem(latestItem)
        bottom_navigation.addItem(compItem)
        bottom_navigation.addItem(teamItem)
        bottom_navigation.addItem(clubItem)
        bottom_navigation.addItem(moreItem)


        bottom_navigation.defaultBackgroundColor = ContextCompat.getColor(this, R.color.bottom_navigation_bg)
        bottom_navigation.accentColor = ContextCompat.getColor(this, R.color.bottom_navigation_accent)
        bottom_navigation.inactiveColor = ContextCompat.getColor(this, R.color.bottom_navigation_inactive)
        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottom_navigation.isBehaviorTranslationEnabled = false


        bottom_navigation.setOnTabSelectedListener(this)
        toLatestTab()


        /* Now check if there is any intent from notifications */
        val notificationURL:String? = intent.getStringExtra("NOTIFICATION_URL")
        if(notificationURL != null && notificationURL.isNotBlank()){
            switchToNewsTabAndShowArticle(notificationURL)
        }

    }


    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        showFragmentFor(position)
        return true
    }




    private fun showFragmentFor(position:Int){

        when(position){
            0 -> toLatestTab()
            1 -> toNewsTab()
            2 -> toTeamTab(0)
            3 -> toClubTab(0)
            4 -> toMoreTab()
        }
    }


    /* Tab Switcher Methods */

    fun toLatestTab(){

        val fragment = LatestFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(0, false)

    }

    fun toNewsTab(){
        val fragment = NewsStreamFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(1, false)
    }


    fun toTeamTab(subTab:Int){
        val fragment = TeamFragment.makeFragment(subTab)
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(2, false)
    }

    fun toClubTab(subTab:Int){
        val fragment = ClubFragment.makeFragment(subTab)
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(3, false)
    }

    fun toMoreTab(){
        val fragment = MoreListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(4, false)
    }

    fun toMatchCenter(action:String){
        val act = Intent(this, MatchCenterUI::class.java)
        act.setAction(action)
        startActivity(act)
    }


    /**
     * Check and display copyright/disclaimer message.
     */

    private fun isFirstCopyright():Boolean{

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return sp.getBoolean("IS_FIRST_CR", true)
    }

    private fun setFirstCopyright(){
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("IS_FIRST_CR", false)
        editor.apply()
    }

    fun showDisclaimer():Boolean{
        val dBuilder = AlertDialog.Builder(this)
        dBuilder.setTitle("Copyright & Disclaimer")
        dBuilder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_disclaimer, null, false))
        dBuilder.setPositiveButton("Ok", null)
        dBuilder.create().show()
        return true
    }


    /**
     * Handle Notifications with URL
     */

    private fun switchToNewsTabAndShowArticle(url:String){
       /* toLatestTab()
        val i = Intent(this, WebViewActivity::class.java)
        i.putExtra("URL", url)

        if (url.contains("youtube")) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                startActivity(i)
            }
        } else startActivity(i)*/
    }









    /**
     * Google Play Services Check
     *
     **/

    val kPlayServicesInstallReqCode = 1000

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


    override fun onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        subscribeFirebaseTopics()
    }

    private fun subscribeFirebaseTopics(){
        FirebaseMessaging.getInstance().subscribeToTopic("NewsUpdatePingv2")

        if(BuildConfig.DEBUG) {
            FirebaseMessaging.getInstance().subscribeToTopic("v2DebugNotifications")
        }

        val settings = SettingsHelper(this)

        if(settings.getUserCurrentCountry().equals("Nepal", true)){
            FirebaseMessaging.getInstance().subscribeToTopic("v2CFCNEventsNotifications")
        }else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2CFCNEventsNotifications")

        if(settings.shouldShowGenEvents())
            FirebaseMessaging.getInstance().subscribeToTopic("v2NextMatchTopic")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2NextMatchTopic")

        if(settings.shouldShowLineups())
            FirebaseMessaging.getInstance().subscribeToTopic("v2NextMatchTopicLineups")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2NextMatchTopicLineups")

        if(settings.shouldShowGoals())
            FirebaseMessaging.getInstance().subscribeToTopic("v2LiveScoresgoals")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2LiveScoresgoals")


        if(settings.shouldShowCards()) FirebaseMessaging.getInstance().subscribeToTopic("v2LiveScorescards")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2LiveScorescards")


        if(settings.shouldShowPen())
            FirebaseMessaging.getInstance().subscribeToTopic("v2LiveScorespens")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2LiveScorespens")

        if(settings.shouldShowSubs())
            FirebaseMessaging.getInstance().subscribeToTopic("v2LiveScoressubs")
        else FirebaseMessaging.getInstance().unsubscribeFromTopic("v2LiveScoressubs")


    }



}