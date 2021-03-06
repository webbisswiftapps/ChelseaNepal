package com.webbisswift.cfcn.ui.screens.splash;

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import com.webbisswift.cfcn.R
import com.webbisswift.cfcn.ui.screens.home.MainActivity
import com.webbisswift.cfcn.utils.FontManager
import com.webbisswift.cfcn.utils.Utilities
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {


    val mHandler:Handler =  Handler()
    lateinit var mPlayer:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //initialize
        mPlayer = MediaPlayer.create(this, R.raw.cfc_chant_carefree_short)
        mPlayer.setVolume(0.25f, 0.25f)

        initActivity()
    }


    private fun initActivity(){
        //set copyright message
        copyrightText.text = Utilities.getCopyrightDate()


        FontManager.getInstance(this).cacheAllFonts()

        if(isFirstSplash()) {
            playChant()
            setFirstSplash()
        }else {
            moveToHome()
        }
    }


    private fun playChant(){
        try {
            mPlayer.setOnCompletionListener {
                //played media, now release and go to next activity
                mPlayer.release()
                mHandler.postDelayed({
                    moveToHome()
                }, 100)
            }
            mPlayer.start()
        }catch (e:Exception){
            e.printStackTrace()
            moveToHome()
        }
    }


    private fun moveToHome(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()

    }

    private fun isFirstSplash():Boolean{

        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return sp.getBoolean("IS_FIRST", true);
    }

    private fun setFirstSplash(){
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("IS_FIRST", false)
        editor.apply()
    }

}
