package dev.chu.sealed_class_manage_ui.data

import dev.chu.sealed_class_manage_ui.data.model.UserModel

/**
 * sealed class 의 모든 서브 클래스는 컴파일 타임에 알려지며,
 * 깔끔하고 이해하기 쉬운 코드를 작성하는데 도움을 준다.
 */
sealed class MainScreenState {
    data class Success(val data: UserModel) : MainScreenState()
    data class Error(val message: String) : MainScreenState()
    object Loading : MainScreenState()
}