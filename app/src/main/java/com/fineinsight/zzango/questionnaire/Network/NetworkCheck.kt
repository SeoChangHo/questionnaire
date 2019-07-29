package com.fineinsight.zzango.questionnaire.Network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class NetworkCheck {

    object Func
    {
        fun Check(activity: Activity):Boolean
        {
            val cm = activity.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = cm.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected)
            {
                println("TYPE: ${networkInfo.type}")
                println("ConnectivityManager.TYPE_WIFI: ${ConnectivityManager.TYPE_WIFI}")
                println("ConnectivityManager.TYPE_MOBILE: ${ConnectivityManager.TYPE_MOBILE}")
                if (networkInfo.type == ConnectivityManager.TYPE_WIFI)
                {
                    println("디바이스가 와이파이로 연결되어 있습니다.")
                }

                if (networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                {
                    println("디바이스가 모바일데이터로 연결되어 있습니다.")
                }
                return true
            }
            else
            {
                println("디바이스가 네트워크에 연결되어 있지 않습니다.")
                return false
            }

        }
    }
}