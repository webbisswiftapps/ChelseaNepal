package com.webbisswift.cfcn.ui.screens.splash;

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //initialize

        initActivity()
    }


    private fun initActivity(){
        //set copyright message
        copyrightText.text = Utilities.getCopyrightDate()


        FontManager.getInstance(this).cacheAllFonts()
        playChant()
    }


    private fun playChant(){
        val mPlayer = MediaPlayer.create(this, R.raw.cfc_chant_carefree_short)
        mPlayer.setVolume(0.5f, 0.5f)
        mPlayer.setOnCompletionListener {
            //played media, now release and go to next activity
            mPlayer.release()
            mHandler.postDelayed({
                moveToHome()
            }, 200)
        }
        mPlayer.start()
    }


    private fun moveToHome(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()

    }



}
