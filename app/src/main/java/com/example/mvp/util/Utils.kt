package com.example.mvp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by 3223 on 06/01/17.
 */

object Utils {

    /**
     * check email validations.
     *
     * @param target
     * @return
     */
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


    /**
     * Method is used for checking network availability.
     *
     * @param context
     * @return isNetAvailable: boolean true for Internet availability, false
     * otherwise
     */
    fun isNetworkAvailable(context: Context?): Boolean {

        var isNetAvailable = false
        if (context != null) {

            val mConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (mConnectivityManager != null) {

                var mobileNetwork = false
                var wifiNetwork = false

                var mobileNetworkConnected = false
                var wifiNetworkConnected = false

                val mobileInfo =
                    mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                val wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

                if (mobileInfo != null) {

                    mobileNetwork = mobileInfo.isAvailable
                }

                if (wifiInfo != null) {

                    wifiNetwork = wifiInfo.isAvailable
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {

                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting
                    }
                    wifiNetworkConnected = wifiInfo!!.isConnectedOrConnecting
                }
                isNetAvailable = mobileNetworkConnected || wifiNetworkConnected
            }
        }

        return isNetAvailable
    }



}
