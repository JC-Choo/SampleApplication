package dev.chu.twowaybinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SampleUser : BaseObservable() {

    var userName: String = "Andro"
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }
}