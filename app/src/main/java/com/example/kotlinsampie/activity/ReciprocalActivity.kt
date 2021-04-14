package com.example.kotlinsampie.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsampie.R
import kotlinx.android.synthetic.main.activity_reciprocal.*
import java.util.*

class ReciprocalActivity : AppCompatActivity() {


    var tt = 11 //設置初始秒數

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reciprocal)

        TimeTask()
    }


    private fun TimeTask() {
        val timer = Timer() //宣告一個時間函數
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    tt-- //時間倒數
                    ReciprocalTextView.setText(Integer.toString(tt))
                    if (tt < 1) {
                        tt = 11
                    }
                }
            }
        }
        timer.schedule(timerTask, 1000, 1000) //時間在幾毫秒過後開始以多少毫秒執行
    }
}