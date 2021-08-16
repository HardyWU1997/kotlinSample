package com.example.kotlinsampie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsampie.R
import com.example.kotlinsampie.model.StudentModel


class SQLiteAdapter:RecyclerView.Adapter<SQLiteAdapter.SQLiteViewHolder>() {

    //===========SQLite 1 Sample

    private var stdList:ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel)->Unit)? = null
    private var onClickDeleteItem:((StudentModel)->Unit)? = null

    fun addItems(items:ArrayList<StudentModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback:(StudentModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SQLiteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.sql_items,parent,false)
    )

    override fun onBindViewHolder(holder: SQLiteViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class SQLiteViewHolder(view: View):RecyclerView.ViewHolder(view){
        private var id  = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std:StudentModel){
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email

        }
    }
}