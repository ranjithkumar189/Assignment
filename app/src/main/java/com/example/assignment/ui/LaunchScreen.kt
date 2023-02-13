package com.example.assignment.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityLaunchScreenBinding

@SuppressLint("CustomSplashScreen")
class LaunchScreen : AppCompatActivity() {

    private lateinit var binding : ActivityLaunchScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference =  getSharedPreferences("ASSIGNMENT", Context.MODE_PRIVATE)
        val isLogin = sharedPreference.getBoolean("isLogin",false)

        //DeepLink Url - https://assignment.fincare.com/homescreen
        try {
            val uri = intent.data
            if (uri != null) {
                val parameters: List<String> = uri.pathSegments
                val param = parameters[parameters.size - 1]
                if (param == "homescreen"){
                    if (isLogin){
                        val intent = Intent(this@LaunchScreen, HomeScreen::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@LaunchScreen, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@LaunchScreen,"Please Login",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    if (isLogin){
                        val intent = Intent(this@LaunchScreen, HomeScreen::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@LaunchScreen, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }else{
                if (isLogin){
                    val intent = Intent(this@LaunchScreen, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@LaunchScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }catch (e : Exception){
            if (isLogin){
                val intent = Intent(this@LaunchScreen, HomeScreen::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@LaunchScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}