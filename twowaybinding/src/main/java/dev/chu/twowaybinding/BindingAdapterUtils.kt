package dev.chu.twowaybinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapterUtils {
    @JvmStatic
    @BindingAdapter("app:updateVisibility")
    fun updateVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:setData")
    internal fun TextView.setData(data: String) {
        text = data
    }

//    @JvmStatic
//    @BindingAdapter("app:loadImageUrl")
//    fun loadImageUrl(imageView: ImageView, url: String?) {
//        Glide.with(imageView.context).load(url)
//            .into(imageView)
//    }
//
//    @JvmStatic
//    @BindingAdapter("app:loadImageUrlData")
//    fun ImageView.loadImageUrlData( url: String?) {
//        Glide.with(this.context).load(url)
//            .into(this)
//    }
}