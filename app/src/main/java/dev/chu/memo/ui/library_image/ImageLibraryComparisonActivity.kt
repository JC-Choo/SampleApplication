package dev.chu.memo.ui.library_image

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityImageLibraryBinding

class ImageLibraryComparisonActivity : BaseActivity<ActivityImageLibraryBinding>() {
    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_image_library

    private val url = "https://cdn.pixabay.com/photo/2015/06/08/15/02/pug-801826_960_720.jpg"

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            activity = this@ImageLibraryComparisonActivity
        }
    }

    fun onClickPicasso() {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.location_plenty)
            .into(binding.imageLibraryIv)
    }

    fun onClickGlide() {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.location_plenty)
            .into(binding.imageLibraryIv2)
    }
}