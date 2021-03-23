package com.example.kotlinsampie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.kotlinsampie.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var topTextView: TextView
    private lateinit var middleTextView: TextView
    private lateinit var bottomTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        topTextView = findViewById(R.id.TopTextView)
        middleTextView = findViewById(R.id.middleTextView)
        bottomTextView = findViewById(R.id.bottomTextView)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        topTextView.startAnimation(topAnimation)
        middleTextView.startAnimation(middleAnimation)
        bottomTextView.startAnimation(bottomAnimation)

        val splashScreenTimesOut = 4000
        val homeIntent = Intent(this@SplashScreenActivity,MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        },splashScreenTimesOut.toLong())
    }
}