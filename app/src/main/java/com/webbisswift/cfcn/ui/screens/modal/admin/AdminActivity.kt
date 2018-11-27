package com.webbisswift.cfcn.ui.screens.modal.admin

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import android.R.attr.startYear
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import com.webbisswift.cfcn.utils.Utilities
import java.util.*







/**
 * Created by apple on 1/10/18.
 */

class AdminActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{



    private val requestQueue = CFCNepalApp.instance?.requestQueue
    private lateinit var datePickerDialog:DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    val setDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_admin_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val doM = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
         datePickerDialog = DatePickerDialog(this, this, year, month, doM)
        timePickerDialog = TimePickerDialog(this, this, 0, 0 , true)
        setListeners()
    }


    private fun setListeners(){

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


        scheduleEvent.setOnClickListener{
            var title:String = eventTitleEntry.text.toString()

            if(title.isBlank()) title = "CFCN Event"

            var msg = eventDescEntry.text.toString()
            val date = eventDateEntry.text.toString()

            if(!msg.isBlank() && !date.isBlank()){
                msg = msg + " \n Time: " + date

                if (msg.isNotBlank()) {
                    val encodedMessage = URLEncoder.encode(msg, "UTF-8")
                    val encodedTitle = URLEncoder.encode(title, "UTF-8")
                    val url = "http://api.cfcn.tk/sendPush?t=" + encodedTitle + "&msg=" + encodedMessage + "&topic=v2CFCNEventsNotifications";
                    runURL(url)
                }
            }else{
                Snackbar.make(coordinatorLayout, "Please enter all values", Snackbar.LENGTH_LONG).show()
            }
        }


        eventDateEntry.setOnClickListener{
            datePickerDialog.show()
        }



    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        setDate.set(year, monthOfYear, dayOfMonth)
        timePickerDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, hr: Int, min: Int) {
        setDate.set(Calendar.HOUR_OF_DAY, hr)
        setDate.set(Calendar.MINUTE, min)
        setDate.set(Calendar.SECOND, 0)

        eventDateEntry.setText(Utilities.getLocaleFormattedDate(setDate.time))
    }

    private fun runURL(url:String){

        val notSB = Snackbar
                .make(coordinatorLayout, "Please Wait... ", Snackbar.LENGTH_SHORT)
        notSB.show()

        val request = StringRequest(Request.Method.GET, url, { response ->
            showDialogOfResponse(response)
        }, { error ->
            error.printStackTrace()
            val snackbar = Snackbar
                    .make(coordinatorLayout, "  Error: "+error.message, Snackbar.LENGTH_LONG)
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