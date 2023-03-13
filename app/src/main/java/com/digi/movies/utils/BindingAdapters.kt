package com.digi.movies.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.digi.movies.R
import com.squareup.picasso.Picasso


@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, iconUrl: String?) {
    val context = imageView.context
    val fullUrl="https://image.tmdb.org/t/p/w500/${iconUrl}"
    Log.e("imageUrl","==$fullUrl")

    Picasso.get()
        .load(fullUrl)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView);
}


