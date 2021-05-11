package dev.chu.sealed_class_manage_ui.data.api

import dev.chu.sealed_class_manage_ui.data.model.UserModel

/**
 * MainApi 는 anonymous class 의 객체이자, 하나의 메소드를 가진 인터페이스의 실현이다.
 * 여기엔 retrofit, database 또는 다른 소스를 넣을 수 있다.
 */
interface MainApi {
    suspend fun getData(): UserModel
}