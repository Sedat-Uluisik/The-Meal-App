package com.example.themealapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//xml kısmından resim indirme işlemleri için.
fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
            .placeholder(progressDrawable)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 7F
        centerRadius = 30F
        start()
    }
}

@BindingAdapter("android:downloadfromurl")
fun downloadImage(imageView: ImageView, url: String?){
    imageView.downloadFromUrl(url, placeHolderProgressBar(imageView.context))
}