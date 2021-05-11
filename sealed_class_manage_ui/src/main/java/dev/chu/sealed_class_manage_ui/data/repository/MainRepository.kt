package dev.chu.sealed_class_manage_ui.data.repository

import dev.chu.sealed_class_manage_ui.data.model.UserModel

/**
 * 잘 관리된 애플리케이션을 만들기 위해 구현에 로직을 숨기는 인터페이스를 작업한다.
 */
interface MainRepository {
    suspend fun getData(): UserModel
}