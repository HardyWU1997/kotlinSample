package com.example.kotlinsampie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsampie.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    //======自定義顯示內容陣列(文字及圖片)
    private var title = arrayOf("Chapter One","Chapter Two","Chapter Three","Chapter Four")
    private var details = arrayOf("Item one details","Item two details","Item Three details","Item Four details")
    private var images = intArrayOf(R.drawable.building_soon,R.drawable.building_soon,R.drawable.building_soon,R.drawable.building_soon)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDetail.text = details[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemDetail:TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener { val position:Int = adapterPosition

                Toast.makeText(itemView.context,"你點擊了 ${title[position]}",Toast.LENGTH_LONG).show()
            }
        }
    }
}