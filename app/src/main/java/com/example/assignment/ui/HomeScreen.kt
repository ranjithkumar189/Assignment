package com.example.assignment.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.assignment.databinding.ActivityHomeScreenBinding
import com.example.assignment.utils.App
import com.example.assignment.utils.BaseActivity

class HomeScreen : BaseActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.resetSession()

        binding.logOutButton.setOnClickListener {
            logout()
            val intent = Intent(this@HomeScreen, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this@HomeScreen , object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                logout()
                finish()
                Toast.makeText(this@HomeScreen,"You have successfully logged out!" ,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun logout() {
        val sharedPreference =  getSharedPreferences("ASSIGNMENT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("isLogin",false)
        editor.apply()
    }

    private fun loginCheck():Boolean {
        val sharedPreference = getSharedPreferences("ASSIGNMENT", Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("isLogin", false)
    }

    override fun onResume() {
        super.onResume()
        if(!loginCheck()){
            if (!isAppBackground(applicationContext)){
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
                Toast.makeText(this@HomeScreen,"Session expired, please login again!" ,Toast.LENGTH_SHORT).show()
            }
        }
    }
}