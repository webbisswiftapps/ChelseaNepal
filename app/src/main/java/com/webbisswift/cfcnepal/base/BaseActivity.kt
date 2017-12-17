package com.webbisswift.cfcnepal.base

import android.view.ViewGroup
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity



/**
 * Created by apple on 12/3/17.
 */

/**
 * Created by biswas on 25/03/2017.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    abstract internal fun getPresenter() : BasePresenter

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /* LifeCycle */
    override fun onStart() {
        super.onStart()
        getPresenter().attachView(this)
    }



    override fun onResume() {
        super.onResume()
        getPresenter().resume()
    }

    override fun onPause() {
        super.onPause()
        getPresenter().pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter().destroy()
    }


}