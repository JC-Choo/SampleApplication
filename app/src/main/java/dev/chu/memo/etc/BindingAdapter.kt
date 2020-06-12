package dev.chu.memo.etc

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import dev.chu.memo.R
import dev.chu.memo.common.LoadingState
import dev.chu.memo.data.local.ImageData
import dev.chu.memo.data.local.MemoData
import dev.chu.memo.ui.memo.MemoAdapter
import dev.chu.memo.ui.memo.MemoViewModel

object BindingAdapter {

    @BindingAdapter("android:srcGlide")
    @JvmStatic
    fun setImageView(view: ImageView, imageUri: String?) {
        GlideApp.with(view.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .transform(CenterCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(view)
    }

    @BindingAdapter("android:srcGlideIsBaseLine")
    @JvmStatic
    fun setImageView(view: ImageView, isBaseLine: Boolean) {
        view.setImageResource(if(isBaseLine) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24)
//        GlideApp.with(view.context)
//            .load(if(isBaseLine) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24)
//            .apply(
//                RequestOptions()
//                    .transform(CenterCrop())
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            )
//            .into(view)
    }

    @BindingAdapter("android:srcGlideCircle")
    @JvmStatic
    fun setImageViewCircle(view: ImageView, imageUri: String?) {
        GlideApp.with(view.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .transform(CenterCrop(), CircleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .into(view)
    }

    @BindingAdapter("android:srcGlide")
    @JvmStatic
    fun setImageView(view: ImageView, imageUrls: List<ImageData>? = null) {
        if(!imageUrls.isNullOrEmpty()) {
            GlideApp.with(view.context)
                .load(imageUrls[0].imageUrl)
                .apply(
                    RequestOptions()
                        .transform(CenterCrop())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
                .into(view)
        }
    }

    @BindingAdapter("android:isVisibility")
    @JvmStatic
    fun setVisibility(view: View, data: MutableList<*>? = null) {
        view.visibility =
            if(data.isNullOrEmpty()) View.GONE
            else View.VISIBLE
    }

    @BindingAdapter(value = ["android:data", "android:vm"])
    @JvmStatic
    fun RecyclerView.setListItem(data: MutableList<MemoData>, vm: MemoViewModel) {
        (adapter as? MemoAdapter)?.apply {
            setItems(data)
        } ?: run {
            this.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_250)))
            adapter = MemoAdapter(
                this.context,
                data,
                vm
            )
        }
    }

    @BindingAdapter(value = ["android:setAdapter"])
    @JvmStatic
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    @BindingAdapter(value = ["android:setupVisibility"])
    @JvmStatic
    fun ProgressBar.progressVisibility(loadingState: LoadingState?) {
        loadingState?.let {
            isVisible = when(it.status) {
                LoadingState.Status.RUNNING -> true
                LoadingState.Status.SUCCESS -> false
                LoadingState.Status.FAILED -> false
            }
        }
    }
}