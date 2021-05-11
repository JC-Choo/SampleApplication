package dev.chu.sealed_class_manage_ui.data.repository

import dev.chu.sealed_class_manage_ui.data.api.MainApi
import dev.chu.sealed_class_manage_ui.data.model.UserModel

class MainRepositoryImpl : MainRepository {

    private val api: MainApi = object : MainApi {
        override suspend fun getData(): UserModel {
            return UserModel("Mike", 25, "super_mike@gmail.com")
        }
    }

    override suspend fun getData(): UserModel {
        val random = (0..1).random()
        if (random == 0) {
            return api.getData()
        } else {
            throw Exception()
        }
    }
}