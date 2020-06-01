package dev.chu.memo.ui.single_view_state.profile

//data class ProfileViewState(
//    val errored: Boolean = false,
//    val loading: Boolean = false,
//    val name: String? = null,
//    val email: String? = null
//)

// sealed class 의 장점 :
// 그 자체로 필요한 자격을 갖춘, 완전한 클래스라는 것. 즉, 서로 독립적으로 각각 고유한 속성 집합을 가질 수 있다.
sealed class ProfileViewState
object Loading : ProfileViewState()
object Error : ProfileViewState()
data class ProfileLoaded(
    val name: String,
    val email: String
) : ProfileViewState()