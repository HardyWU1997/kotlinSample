package com.example.kotlinsampie.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.kotlinsampie.R
import kotlinx.android.synthetic.main.activity_dialog.*
import java.util.*

class DialogActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var AlertDialogBtn:Button
    private lateinit var ProgressDialogBtn:Button
    private lateinit var DataPickerBtn:Button
    private lateinit var TimePickerBtn:Button
    private lateinit var date:String
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        AlertDialogBtn = findViewById(R.id.AlertBtn)
        ProgressDialogBtn = findViewById(R.id.ProgressBtn)
        DataPickerBtn = findViewById(R.id.DataPickerBtn)
        TimePickerBtn = findViewById(R.id.TimePickerBtn)
        editText = findViewById(R.id.editText)

        AlertDialogBtn.setOnClickListener(this)
        ProgressDialogBtn.setOnClickListener(this)
        DataPickerBtn.setOnClickListener(this)
        TimePickerBtn.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.AlertBtn ->{
                AlertDialogMethod()
            }
            R.id.ProgressBtn ->{
                ProgressDialogMethod()
            }
            R.id.DataPickerBtn ->{
                DatePickerMethod()
            }
            R.id.TimePickerBtn ->{
                TimePickerMethod()
            }
        }

        }


    private fun TimePickerMethod() {
        var c: Calendar = Calendar.getInstance()
        var tp = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                date = date + "  $hourOfDay:$minute"
                editText.setText(date)
            }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false
        )
        tp.show()
    }

    private fun DatePickerMethod() {
        var c: Calendar = Calendar.getInstance()
        var dp = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                date = "$year/${month+1}/$dayOfMonth"
                editText.setText(date)

            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        dp.show()
    }

    private fun ProgressDialogMethod() {
        var pd = ProgressDialog(this)
        pd.setTitle("請稍後...")
        pd.setMessage("檔案下載中...")
        pd.max = 100
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        pd.setCancelable(false)
        pd.show()

        Thread(Runnable {
            var count = 0
            while (count <= 100) {
                try {
                    pd.progress = count
                    count += 5
                    Thread.sleep(1000)
                } catch (i: InterruptedException) {

                }
            }
            pd.dismiss()
        }).start()
    }


    private fun AlertDialogMethod() {
        var ad = AlertDialog.Builder(this)
        ad.setMessage("今天是2021.03.23")
        ad.setTitle("Hi!")
        ad.setCancelable(false)
        ad.setPositiveButton("好", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(applicationContext,"很棒!",Toast.LENGTH_LONG).show()
        })
        ad.setNegativeButton("不好", null)
        ad.show()
    }
}