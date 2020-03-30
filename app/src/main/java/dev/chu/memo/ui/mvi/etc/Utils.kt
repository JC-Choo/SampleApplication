package dev.chu.memo.ui.mvi.etc

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

/**
 * This is a custom NoObserverAttachedException and it does what it's name suggests.
 * Constructs a new exception with the specified detail message.
 * This is thrown, if you have not attached any observer to the LiveData.
 */
class NoObserverAttachedException(message: String) : Exception(message)