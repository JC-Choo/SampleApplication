package dev.chu.sealed_class_manage_ui.etc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.sealed_class_manage_ui.MainApplication
import dev.chu.sealed_class_manage_ui.ui_main.MainViewModel
import dev.chu.sealed_class_manage_ui.data.ResourceManager
import dev.chu.sealed_class_manage_ui.data.repository.MainRepositoryImpl

class ViewModelFactory : ViewModelProvider.Factory {

    companion object {
        private var factory: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory {
            if (factory == null) {
                synchronized(ViewModelFactory::class) {
                    if (factory == null) {
                        factory = ViewModelFactory()
                    }
                }
            }

            return factory!!
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val repository = MainRepositoryImpl()
            val resource = ResourceManager(MainApplication.get())
            return MainViewModel(repository, resource) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}