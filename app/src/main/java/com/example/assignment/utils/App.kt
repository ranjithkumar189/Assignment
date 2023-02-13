package com.example.assignment.utils

import android.app.Application
import android.util.Log
import java.util.*


class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private var logoutListener: LogoutListener? = null
        private var timer: Timer? = null
        private fun userSessionStart() {
            timer?.cancel()
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (logoutListener != null) {
                        logoutListener!!.onSessionLogout()
                        Log.d("APP", "Session Destroyed")
                    }
                }
            }, 1000 * 60 * 1)
        }

        fun resetSession() {
            userSessionStart()
        }

        fun registerSessionListener(listener: LogoutListener?) {
            logoutListener = listener
        }
    }
}