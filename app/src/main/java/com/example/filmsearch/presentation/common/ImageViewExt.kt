package com.example.filmsearch.presentation.common

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.filmsearch.R

fun ImageView.setImageUrl(url:String) {
    val circle = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_baseline_image_24)
        .placeholder(circle)
        .into(this)
}