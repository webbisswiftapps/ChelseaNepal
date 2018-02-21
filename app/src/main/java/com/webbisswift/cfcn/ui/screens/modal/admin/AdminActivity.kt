package com.webbisswift.cfcn.ui.screens.modal.admin

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.webbisswift.cfcn.R
import kotlinx.android.synthetic.main.layout_admin_activity.*
import android.support.design.widget.Snackbar
import com.webbisswift.cfcn.root.CFCNepalApp
import java.net.URLEncoder


/**
 * Created by apple on 1/10/18.
 */

class AdminActivity : AppCompatActivity(){



    private val requestQueue = CFCNepalApp.instance?.requestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_admin_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListeners()
    }


    private fun setListeners(){
        startLiveButton.setOnClickListener {
            runURL("https://api.cfcn.tk/startLive")
        }

        updateTVInfo.setOnClickListener{
            runURL("https://api.cfcn.tk/updateTVListing")
        }


        updateFixtures.setOnClickListener{
            runURL("https://api.cfcn.tk/updateFixtures")
        }

        updateResults.setOnClickListener{
            runURL("https://api.cfcn.tk/updateResults")
        }

        updateTables.setOnClickListener{
            runURL("https://api.cfcn.tk/updateTables")
        }

        updateTeamInfo.setOnClickListener{
            runURL("https://api.cfcn.tk/updateTeamInfo")
        }

        updateNews.setOnClickListener{
            runURL("https://api.cfcn.tk/updateNews")
        }


        sendNotification.setOnClickListener {
            var title:String = notTitleEntry.text.toString()

            if(title.isBlank()) title = "CFCN"
            val msg = notMsgEntry.text.toString()

            if(msg.isNotBlank()){
                val encodedMessage = URLEncoder.encode(msg, "UTF-8")
                val encodedTitle = URLEncoder.encode(title, "UTF-8")
                val url = "https://api.cfcn.tk/sendPush?t="+encodedTitle+"&msg="+encodedMessage
                runURL(url)
            }

        }



    }



    private fun runURL(url:String){

        val notSB = Snackbar
                .make(coordinatorLayout, "Running URL : "+url, Snackbar.LENGTH_SHORT)
        notSB.show()

        val request = StringRequest(Request.Method.GET, url, { response ->
            showDialogOfResponse(response)
        }, { error ->
            val snackbar = Snackbar
                    .make(coordinatorLayout, url+"  Error: "+error.message, Snackbar.LENGTH_LONG)
            snackbar.show()
        })
        request.setTag(this)
        requestQueue?.add(request)
    }



    private fun showDialogOfResponse(response:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Response")
        dialogBuilder.setMessage(response)
        dialogBuilder.setPositiveButton("Ok", null)
        dialogBuilder.create().show()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return true
    }


    override fun onDestroy() {
        requestQueue?.cancelAll(this)
        super.onDestroy()
    }




}