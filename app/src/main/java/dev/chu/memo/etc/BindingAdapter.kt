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
    fun setImageViewCircle(view: ImageView, imageUri: String?) {
        Glide.with(view.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .transforms(CenterCrop(), CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(view)
    }

    @BindingAdapter("android:srcGlide")
    @JvmStatic
    fun setImageView(view: ImageView, imageUri: String?) {
        Glide.with(view.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .transform(CenterCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(view)
    }
}