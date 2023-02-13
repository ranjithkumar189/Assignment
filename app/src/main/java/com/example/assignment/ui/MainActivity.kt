package com.example.assignment.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            login()
            val intent = Intent(this@MainActivity, HomeScreen::class.java)
            startActivity(intent)
        }


    }

    private fun login() {
        val sharedPreference =  getSharedPreferences("ASSIGNMENT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("isLogin",true)
        editor.apply()
    }
}