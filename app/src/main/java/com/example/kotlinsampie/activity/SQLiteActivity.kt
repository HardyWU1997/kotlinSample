package com.example.kotlinsampie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsampie.R
import com.example.kotlinsampie.adapter.SQLiteAdapter
import com.example.kotlinsampie.model.StudentModel
import com.example.kotlinsampie.utils.SQLiteHelper
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_sqlite.*
import org.jetbrains.anko.toast

class SQLiteActivity : AppCompatActivity() {

    //SQLite 1 smaple

    private lateinit var sqliteHelper:SQLiteHelper

    private var adapter:SQLiteAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        Stetho.initializeWithDefaults(this)
        sqliteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudent() }
        btnUpdate.setOnClickListener { updateStudent() }

        initRecyclerView()
        getStudent()
        adapter?.setOnClickItem {
            Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun addStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"請輸入資料",Toast.LENGTH_LONG).show()
        }else{
            val std = StudentModel(name = name,email = email)
            val status = sqliteHelper.insertStudent(std)

            Log.d("Receiver_DATA","STATUS:$status")

            if (status > -1){
                Toast.makeText(this,"Student Added...",Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            }else{
                Toast.makeText(this,"新增失敗",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        //Check record not change
        if (name == std?.name && email == std?.email){
            Toast.makeText(this,"資料未更新...",Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null)return

        val std = StudentModel(id = std!!.id,name = name,email = email)
        val status = sqliteHelper.updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudent()
        }else{
            Toast.makeText(this,"更新失敗",Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(id:Int){
        if (id == null)return
        val bundle = AlertDialog.Builder(this)
        bundle.setMessage("您確定要刪除此筆資料嗎?")
        bundle.setCancelable(true)
        bundle.setPositiveButton("確定"){
            dialog,_ ->
            sqliteHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        bundle.setNegativeButton("取消"){
            dialog,_ ->
            dialog.dismiss()
        }

        val alert = bundle.create()
        alert.show()
    }

    private fun clearEditText(){
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SQLiteAdapter()
        recyclerView.adapter = adapter
    }

    private fun getStudent(){
        val stdList = sqliteHelper.getAllStudent()
        Log.d("Receiver_Data","${stdList.size}")

        adapter?.addItems(stdList)
    }
}