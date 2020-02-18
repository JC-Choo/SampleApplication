package dev.chu.memo.etc

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    @BindingAdapter("android:srcGlideCircle")
    @JvmStatic
    fun setImageViewCircle(imageView: ImageView, imageUri: String?) {
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .transforms(CenterCrop(), CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(imageView)
    }
}