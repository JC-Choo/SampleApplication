package dev.chu.memo.ui.mvi.etc

/**
 * ViewModel에 의해 구현될 Internal Contract
 * ViewEvents를 막거나 기록하는데 필요
 */
internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

/**
 * 특정 상세 메시지를 가진 새 Exception을 구성
 * LiveData에 대한 어떤 옵저버도 붙지 않는다면, 이걸 던진다.
 */
class NoObserverAttachedException(message: String) : Exception(message)