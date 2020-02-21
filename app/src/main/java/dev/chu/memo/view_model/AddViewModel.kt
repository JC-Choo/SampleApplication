package dev.chu.memo.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel
import dev.chu.memo.etc.extension.showToast

class AddViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val context = application.applicationContext

    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()

    fun onClickSave() {
        context.showToast("title = ${title.value}, content = ${content.value}")
    }
}