package dev.chu.memo.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dev.chu.memo.base.BaseAndroidViewModel

class AddViewModel(application: Application) : BaseAndroidViewModel(application) {

    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
}