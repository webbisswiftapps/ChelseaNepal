package com.webbisswift.cfcn.base


/**
 * Created by apple on 12/3/17.
 */

interface BasePresenter {


    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    fun resume()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    fun pause()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    fun destroy()

    fun attachView(view: BaseView)
    fun detachView()

}