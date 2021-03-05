package com.lopessoft.projectgithublabs.presentation.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lopessoft.projectgithublabs.R

fun ImageView.loadImage(url: String, context: Context) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions().centerCrop().placeholder(R.drawable.ic_baseline_fiber_manual_record_24)
                .error(R.drawable.ic_launcher_background).disallowHardwareConfig()
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
        )
        .into(this)
}
