package dev.chu.mvvmarchitecture.etc

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.chu.mvvmarchitecture.data.MainRepository
import dev.chu.mvvmarchitecture.ui.MainViewModel

fun ImageView.imageUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

class ViewModelFactory(
    private val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}