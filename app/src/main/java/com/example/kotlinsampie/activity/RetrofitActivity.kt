package com.example.kotlinsampie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kotlinsampie.R
import com.example.kotlinsampie.api.ApiRequests
import kotlinx.android.synthetic.main.activity_retrofit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://cat-fact.herokuapp.com"

class RetrofitActivity : AppCompatActivity() {

    private var TAG = "RetrofitActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        getCurrentData()

        layout_generate_new_fact.setOnClickListener {
            getCurrentData()
        }

    }

    private fun getCurrentData() {

        tv_textView.visibility = View.INVISIBLE
        tv_TimeStamp.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO){

            try {

                val response = api.getCatFacts().awaitResponse()
                if (response.isSuccessful){
                    val data = response.body()!!
                    Log.d(TAG, data.text)
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"Seems like something went wrong...",Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}