package com.example.kotlinsampie.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsampie.R
import com.example.kotlinsampie.fragment.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fragmentBtn:Button
    private lateinit var recyclerBtn:Button
    private lateinit var dialogBtn:Button
    private lateinit var retrofitBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentBtn = findViewById(R.id.fragmentBtn)
        recyclerBtn = findViewById(R.id.recyclerViewBtn)
        dialogBtn = findViewById(R.id.dialogBtn)
        retrofitBtn =  findViewById(R.id.retrofitBtn)

        fragmentBtn.setOnClickListener(this)
        recyclerBtn.setOnClickListener(this)
        dialogBtn.setOnClickListener(this)
        retrofitBtn.setOnClickListener(this)
        recyclerViewStlyeBtn.setOnClickListener(this)
        NFCBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.fragmentBtn -> {
                val intent = Intent()
                intent.setClass(this@MainActivity, FragmentActivity::class.java)
                startActivity(intent)
            }
            R.id.recyclerViewBtn ->{
                val intent = Intent()
                intent.setClass(this@MainActivity, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.dialogBtn ->{
                val intent = Intent()
                intent.setClass(this@MainActivity, DialogActivity::class.java)
                startActivity(intent)
            }
            R.id.retrofitBtn ->{
                val intent =  Intent()
                intent.setClass(this@MainActivity, RetrofitActivity::class.java)
                startActivity(intent)
            }
            recyclerViewStlyeBtn.id ->{
                startActivity(Intent(this, RecyclerViewStyleActivity::class.java))
            }
            R.id.NFCBtn ->{
                startActivity(Intent(this, NfcActivity::class.java))
            }

        }
    }
}