package com.example.assignment.utils

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.ui.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


abstract class BaseActivity : AppCompatActivity(), LogoutListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.registerSessionListener(this)
    }

    override fun onResume() {
        super.onResume()
        App.registerSessionListener(this)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        App.resetSession()
    }

    override fun onSessionLogout() {
        logout()
        if (!isAppBackground(applicationContext)){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
            runOnUiThread{
                Toast.makeText(applicationContext,"Session expired, please login again!" ,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun logout() {
        val sharedPreference =  getSharedPreferences("ASSIGNMENT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("isLogin",false)
        editor.apply()
    }
    fun isAppBackground(context: Context): Boolean {
        var isBackground = true
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            val runningProcesses = activityManager.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.packageName) {
                            isBackground = false
                        }
                    }
                }
            }
        } else {
            val taskInfo = activityManager.getRunningTasks(1)
            if (taskInfo.size > 0) {
                val componentName = taskInfo[0].topActivity
                if (componentName!!.packageName == context.packageName) {
                    isBackground = false
                }
            }
        }
        return isBackground
    }
}