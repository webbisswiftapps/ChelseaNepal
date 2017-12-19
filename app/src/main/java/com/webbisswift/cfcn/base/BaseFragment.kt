package com.webbisswift.cfcn.base

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.View


/**
 * Created by apple on 12/3/17.
 */

abstract class BaseFragment : Fragment(), BaseView{

    abstract fun getPresenter():BasePresenter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)

    }


    override fun onResume() {
        super.onResume()
        getPresenter().resume()
    }

    override fun onPause() {
        getPresenter().pause()
        super.onPause()
    }

    override fun onDetach() {
        getPresenter().detachView()
        super.onDetach()
    }

    override fun onDestroy() {
        getPresenter().destroy()
        super.onDestroy()
    }

}