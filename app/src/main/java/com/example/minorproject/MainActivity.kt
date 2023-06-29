package com.example.minorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var prefer=getSharedPreferences("MyPref", MODE_PRIVATE)
        var str=prefer.getString("UserName","wrong")

        var image: ImageView = findViewById(R.id.logo)
        var text: TextView = findViewById(R.id.tagline)
        var fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        image.startAnimation(fadeIn)
        text.startAnimation(fadeIn)
        Handler().postDelayed({
            if(str.equals("wrong")) {
                var intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            else
            {
                var intent = Intent(applicationContext, ParkMapActivity::class.java)
                startActivity(intent)
            }
        }, 3000)


    }
}