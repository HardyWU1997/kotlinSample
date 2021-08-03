package com.example.kotlinsampie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsampie.R
import com.makeramen.roundedimageview.RoundedImageView
import java.util.*

class TvShowAdapterK(val tvShows: List<TvShow>, var tvShowListener: TvShowListener) :RecyclerView.Adapter<TvShowAdapterK.TvShowViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_container_tv_show, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bindTvShow(tvShows[position])
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    fun getSelectedTvShows(): List<TvShow>? {
        val selectedTvShows: MutableList<TvShow> = ArrayList()
        for (tvShow in tvShows!!) {
            if (tvShow.isSelected) {
                selectedTvShows.add(tvShow)
            }
        }
        return selectedTvShows
    }

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layoutTvShow: ConstraintLayout
        var viewBackground: View
        var imageTvShow: RoundedImageView
        var textName: TextView
        var textCreatedBy: TextView
        var textStory: TextView
        var ratingBar: RatingBar
        var imageSelected: ImageView

        fun bindTvShow(tvShow: TvShow) {
            imageTvShow.setImageResource(tvShow.image)
            textName.text = tvShow.name
            textCreatedBy.text = tvShow.createdBy
            textStory.text = tvShow.story
            ratingBar.rating = tvShow.rating



            if (tvShow.isSelected) {
                viewBackground.setBackgroundResource(R.drawable.tv_show_selected_background)
                imageSelected.visibility = View.VISIBLE
            } else {
                viewBackground.setBackgroundResource(R.drawable.tv_show_background)
                imageSelected.visibility = View.GONE
            }
            layoutTvShow.setOnClickListener {
                if (tvShow.isSelected) {
                    viewBackground.setBackgroundResource(R.drawable.tv_show_background)
                    imageSelected.visibility = View.GONE
                    tvShow.isSelected = false
                    if (getSelectedTvShows()?.size == 0) {
//                        tvShowListener.onTvShowAction(false)
                    }
                } else {
                    viewBackground.setBackgroundResource(R.drawable.tv_show_selected_background)
                    imageSelected.visibility = View.VISIBLE
                    tvShow.isSelected = true
//                       tvShowListener.onTvShowAction(true)
                }
            }
        }

        fun getSelectedTvShows(): List<TvShow>? {
            val selectedTvShows: MutableList<TvShow> = ArrayList()
//            for (tvShow in tvShows) {
//                if (tvShow.isSelected) {
//                    selectedTvShows.add(tvShow)
//                }
//            }
            return selectedTvShows

        }

        init {
            layoutTvShow = itemView.findViewById(R.id.layoutTvShow)
            viewBackground = itemView.findViewById(R.id.viewBackground)
            imageTvShow = itemView.findViewById(R.id.imageTvShow)
            textName = itemView.findViewById(R.id.textName)
            textCreatedBy = itemView.findViewById(R.id.textCreateBy)
            textStory = itemView.findViewById(R.id.textStory)
            ratingBar = itemView.findViewById(R.id.ratingBar)
            imageSelected = itemView.findViewById(R.id.imageSelected)
        }
    }

}