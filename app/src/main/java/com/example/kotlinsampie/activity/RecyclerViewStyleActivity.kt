package com.example.kotlinsampie.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsampie.R
import com.example.kotlinsampie.adapter.TvShow
import com.example.kotlinsampie.adapter.TvShowAdapter
import com.example.kotlinsampie.adapter.TvShowAdapterK
import com.example.kotlinsampie.adapter.TvShowListener
import kotlinx.android.synthetic.main.activity_recycler_view_style.*
import java.util.*

class RecyclerViewStyleActivity : AppCompatActivity(),TvShowListener {

    private lateinit var  buttonAddToWatchlist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_style)

        val tvShowRecyclerView = findViewById<RecyclerView>(R.id.toShowRecyclerView)
        buttonAddToWatchlist = findViewById(R.id.buttonAddToWatchlist)

        val tvShows: MutableList<TvShow> = ArrayList<TvShow>()


        refreshApp()//下拉更新特效

        val breakingBad = TvShow()
        breakingBad.image = R.mipmap.breakingbad
        breakingBad.name = "絕命毒師"
        breakingBad.rating = 5F
        breakingBad.createdBy = "George Vincent Vince Gilligan, Jr."
        breakingBad.story = "一名高中化學老師華特，在罹患肺癌後開啟製作冰毒的一段故事"
        tvShows.add(breakingBad)

        val LaCasaDePaper = TvShow()
        LaCasaDePaper.image = R.mipmap.lacasadepapel
        LaCasaDePaper.name = "紙房子"
        LaCasaDePaper.rating = 5F
        LaCasaDePaper.createdBy = "Han"
        LaCasaDePaper.story = "一群搶匪搶西班牙銀行"
        tvShows.add(LaCasaDePaper)

        val tvShowAdapter = TvShowAdapter(tvShows, this)
        tvShowRecyclerView.setAdapter(tvShowAdapter)

        buttonAddToWatchlist.setOnClickListener {
            val selectedTvShows = tvShowAdapter.selectedTvShows
            val tvShowNames = StringBuilder()
            for (i in selectedTvShows.indices) {
                if (i == 0) {
                    tvShowNames.append(selectedTvShows[i].name)
                } else {
                    tvShowNames.append("\n").append(selectedTvShows[i].name)
                }
            }
            Toast.makeText(this@RecyclerViewStyleActivity, tvShowNames.toString(), Toast.LENGTH_LONG).show()
        }

        //每兩秒更新recyclerView
        val uiChange = Timer()
        val uiChangeTask:TimerTask = object :TimerTask(){
            override fun run() {
                runOnUiThread {
                    tvShowRecyclerView.setAdapter(tvShowAdapter)
                }
            }
        }
        uiChange.schedule(uiChangeTask, 1000, 1000*2) //時間在1秒過後開始以每2秒執行一次

//        val tvShowAdapterK = TvShowAdapterK(tvShows,this)
//        tvShowRecyclerView.setAdapter(tvShowAdapterK)
//
//        buttonAddToWatchlist.setOnClickListener {
//            val selectedTvShows = tvShowAdapterK.getSelectedTvShows()
//            val tvShowNames = StringBuilder()
//            if (selectedTvShows != null) {
//                for (i in selectedTvShows.indices) {
//                    if (i == 0) {
//                        tvShowNames.append(selectedTvShows.get(i).name)
//                    } else {
//                        tvShowNames.append("\n").append(selectedTvShows[i].name)
//                    }
//                }
//            }
//            Toast.makeText(this@RecyclerViewStyleActivity, tvShowNames.toString(), Toast.LENGTH_LONG).show()
//        }


    }

    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {

            swipeToRefresh.isRefreshing = false
        }
    }

    override fun onTvShowAction(isSelected: Boolean) {
        if (isSelected!!) {
            buttonAddToWatchlist.visibility = View.VISIBLE
        } else {
            buttonAddToWatchlist.visibility = View.GONE
        }
    }
}


