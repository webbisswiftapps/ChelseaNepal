package com.webbisswift.cfcn.ui.screens.mainnavigation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.webbisswift.cfcn.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.webbisswift.cfcn.domain.sharedpref.SettingsHelper
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.MoreListFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.club.ClubFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.competitions.CompetitionListFragment
import com.webbisswift.cfcn.ui.screens.modal.AboutUsUI
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.latest.LatestFragment
import com.webbisswift.cfcn.ui.screens.mainnavigation.maintabs.team.TeamFragment
import com.webbisswift.cfcn.ui.screens.modal.admin.AdminActivity
import com.webbisswift.cfcn.ui.screens.modal.match_center.MatchCenterUI
import com.webbisswift.cfcn.ui.screens.modal.settings.SettingsActivity
import com.webbisswift.cfcn.ui.screens.modal.webview.WebViewActivity
import com.webbisswift.cfcn.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_main_bnav.*
import java.util.prefs.PreferenceChangeListener


/**
 * Created by apple on 2/11/18.
 */

class MainNavigationActivity : AppCompatActivity(), AHBottomNavigation.OnTabSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bnav)
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
    }

    private fun setupNavigation(){
        val latestItem = AHBottomNavigationItem(R.string.home, R.drawable.ic_bnav_clock, R.color.text_white)
        val compItem = AHBottomNavigationItem(R.string.competitions, R.drawable.ic_bnav_trophy, R.color.text_white)
        val teamItem = AHBottomNavigationItem(R.string.players, R.drawable.ic_bnav_players, R.color.text_white)
        val clubItem = AHBottomNavigationItem(R.string.team, R.drawable.ic_bnav_club, R.color.text_white)
        val moreItem = AHBottomNavigationItem(R.string.more, R.drawable.ic_bnav_more, R.color.text_white)

        bottom_navigation.addItem(latestItem)
        bottom_navigation.addItem(compItem)
        bottom_navigation.addItem(teamItem)
        bottom_navigation.addItem(clubItem)
        bottom_navigation.addItem(moreItem)


        bottom_navigation.defaultBackgroundColor = Color.WHITE
        bottom_navigation.accentColor = ContextCompat.getColor(this, R.color.colorPrimary)
        bottom_navigation.inactiveColor = ContextCompat.getColor(this, R.color.dark_text)
        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottom_navigation.isBehaviorTranslationEnabled = false


        bottom_navigation.setOnTabSelectedListener(this)
        toLatestTab(0)


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
             0 -> toLatestTab(0)
             1 -> toCompetitionsTab(0)
             2 -> toTeamTab(0)
             3 -> toClubTab(0)
             4 -> toMoreTab()
        }
    }


    /* Tab Switcher Methods */

    fun toLatestTab(subTab: Int){

        val fragment = LatestFragment.makeFragment(subTab)
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        bottom_navigation.setCurrentItem(0, false)

    }

    fun toCompetitionsTab(subtab:Int){
        val fragment = CompetitionListFragment.makeFragment(subtab)
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
        toLatestTab(1)
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
        FirebaseMessaging.getInstance().subscribeToTopic("NewsUpdatePing")

        FirebaseMessaging.getInstance().subscribeToTopic("v2NextMatchTopicLineupsTest")


        val settings = SettingsHelper(this)
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