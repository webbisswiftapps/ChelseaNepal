package com.webbisswift.cfcn.ui.screens.webview

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.webbisswift.cfcn.R
import android.webkit.*
import kotlinx.android.synthetic.main.layout_webview.*


/**
 * Created by apple on 12/10/17.
 */

class WebViewActivity : AppCompatActivity(){


    lateinit var  notificationManager:NotificationManager

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

         this.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val url:String? = intent.getStringExtra("URL")
        if(url!= null){
            startLoading(url)
        }
    }

    override fun onResume(){
        super.onResume()
        notificationManager.cancel(808)
    }

    private fun startLoading(url:String){

        val webSettings = webView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {

                if (progress < 100) {
                    if (progressBar.visibility === View.GONE) progressBar.visibility = View.VISIBLE
                    progressBar.progress = progress
                } else {
                    progressBar.visibility = View.GONE
                }
            }

        }

        webView.webViewClient = object : WebViewClient(){

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("WebViewActivity","Page Load finished")
            }


            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Log.d("WebViewActivity","Recd error: "+error.toString())
                super.onReceivedError(view, request, error)
            }

        }
        webView.loadUrl(url)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_share -> return shareNews()
        }

        return false
    }

    private fun  goBack():Boolean{
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
        return true
    }


    private fun shareNews():Boolean{

        //share current url instead of original url
        val url = webView.url
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(shareIntent, "Share Article"))
        return true

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK) return goBack()

        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}