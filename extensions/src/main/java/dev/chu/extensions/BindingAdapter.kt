package dev.chu.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageViewUrl")
fun ImageView.setImageView(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

@BindingAdapter("imageViewDrawableRes")
fun ImageView.setImageView(@DrawableRes resId: Int) {
    Glide.with(this)
        .load(resId)
        .into(this)
}