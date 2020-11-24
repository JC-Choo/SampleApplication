@file:Suppress("RedundantVisibilityModifier")

package dev.chu.memo.ui.ark

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

fun Context.findActivity(): AppCompatActivity? {
    var tempContext = this
    while(tempContext is ContextWrapper) {
        if(tempContext is AppCompatActivity) {
            return tempContext
        }
        tempContext = tempContext.baseContext
    }
    return null
}

/**
 * [androidx.lifecycle.LiveData]를 통해 observe 할 Event 전송용 wrapper class.
 * - 기 처리된 [content]는 사용할 수 없다.
 * - 에러 메세지와 같은 [message]를 함께 사용 할 수 있다.
 * - [EventObserver]와 함께 사용하도록 한다.
 */
public open class Event<T>(private val content: T, public val message: String? = null) {
    private val handledKey: MutableSet<String> = HashSet()

    /**
     * [content]를 확인해 처리되지 않은 경우 리턴한다.
     * - 기 처리된 [content]는 null 을 리턴한다.
     * - [owner]는 해당 [Event]를 실제 처리하는 객체이다.
     */
    public fun getContentIfNotHandled(owner: EventHandler): T? {
        val hashCode = owner.eventHandleKey

        return if (handledKey.contains(hashCode)) {
            null
        } else {
            handledKey.add(hashCode)
            content
        }
    }

    /**
     * [content]값을 반환한다. (이미 처리된 것 포함)
     */
    public fun peekContent(): T = content
}

/**
 * Event Handler 구분을 위한 Key 인터페이스
 * -  Activity 또는 Fragment 에서 구현을 하게 된다.
 */
interface EventHandler {

    /**
     * Event Handler 를 구분하는 Key 값
     * @see Event.getContentIfNotHandled
     */
    val eventHandleKey: String
}

/**
 * [Event]를 observe 할 때 작성하는 boilerplate 코드를 줄이기 위한 용도의 [Observer] class.
 *  - [owner]는 해당 [Event]를 실제 처리하는 객체이다.
 */
public class EventObserver<T>(
    private val owner: EventHandler,
    private val onEventUnHandledContent: (T, String?) -> Unit
) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled(owner)?.let {
            onEventUnHandledContent(it, event.message)
        }
    }
}