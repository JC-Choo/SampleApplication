package dev.chu.memo.ui.mvi

import dev.chu.memo.entity.User

// STATE EFFECT EVENT

/**
 * ViewState
 * name에서 알 수 있듯 ViewState는 "모델층의 부분"으로써, 우리의 뷰는 상태 변화를 위해 이 모델을 관찰한다.
 * ViewState는 주어진 시간에 뷰의 현재 상태를 표현할 수 있다.
 * 그래서 이 클래스는 우리의 뷰에 의지하는 모든 변수 내용 가질 수 있다.
 * 매번 유저 인풋/액션이 있다면 우리는 이 클래스(수정되지 않는 한 이전 상태를 유지)의 수정본을 노출할 것이다.
 * 우리는 Kotlin’s data class 사용해 이 모델을 만든다.
 */
data class MviViewState(val fetchStatus: FetchStatus, val userList: List<User>)

sealed class FetchStatus {
    object Fetching : FetchStatus()     // 가지고 오는 중
    object Fetched : FetchStatus()      // 가졌다
    object NotFetched : FetchStatus()   // 가지지 못했다
}

/**
 * ViewEffect:
 * 안드로이드에서는, 예를 들어, 토스트와 같은 특정 동작들이 있다. 이러한 경우, 우리는 상태를 유지하는 ViewState를 사용할 수 없다.
 * 그 의미는, 우리가 토스트를 보여줄 때 ViewState를 사용하면, 그것은 'toast is shown' 이벤트를 통과함으로써 그 상태를 재설정할때까지 구성변화 또는 새 상태가 있을때마다 다시 보여질 것이다.
 * 그리고 그걸 하기 원치 않는다면, SingleLiveEvent 를 기반으로 하고 상태를 유지하지 않기 때문에 ViewEffect 를 사용할 수 있다.
 * ViewEffect는 또한 "모델의 일부분"이며, Kotlin’s sealed class를 사용해 만들 수 있다.
 */
sealed class MviViewEffect {
    data class ShowSnackbar(val message: String) : MviViewEffect()
    data class ShowToast(val message: String) : MviViewEffect()
}

/**
 * ViewEvent
 * 유저가 "뷰"에서 행할 수 있는 모든 액션/이벤트들을 표현한다.
 * ViewEvent는 ViewModel로 사용자 인풋/액션을 패스하기 위해 사용된다.
 * 우리는 이벤트를 Kotlin’s sealed class 사용해 설정하게 만든다.
 */
sealed class MviViewEvent {
    data class UsersItemClicked(val item: User) : MviViewEvent()
    object FabClicked : MviViewEvent()
    object OnSwipeRefresh : MviViewEvent()
    object FetchUsers : MviViewEvent()
}