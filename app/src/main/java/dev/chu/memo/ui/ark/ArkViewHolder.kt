package dev.chu.memo.ui.ark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView

/**
 * [androidx.recyclerview.widget.RecyclerView]에 사용할 base [androidx.recyclerview.widget.RecyclerView.ViewHolder].
 *
 * - [layout]에 해당하는 view 를 만든다.
 * - [bindItem]을 override 하여 사용한다.
 * - null 이 binding 되는 경우 디폴트로 빈 layout 을 내려준다.
 * - null 에 대한 추가적인 처리가 필요하면 [bindItemNullable]을 override 한다.
 * - attach/detach, scroll, recycle 과 같은 이벤트 시점에 처리해야 할 로직 필요시, 각 함수들을 override 하여 구현한다.
 */
@Suppress("RedundantVisibilityModifier")
public abstract class ArkViewHolder<Item : ListItem<*>> : RecyclerView.ViewHolder {

    constructor(itemView: View) : super(itemView)
    constructor(parent: ViewGroup, @LayoutRes layoutRes: Int) : this(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    /** [ArkViewHolder]가 속한 [Context]. */
    protected val context: Context
        get() = itemView.context

    /** [ArkViewHolder]가 속한 [AppCompatActivity]. */
    protected val activity: AppCompatActivity?
        get() = context.findActivity()

    /** bind 된 [Item], View 를 그리는데 사용될 데이터. */
    protected lateinit var item: Item

    /** Parallax 효과가 필요한 view 를 갖는 경우, 해당 view 를 리턴하도록 [parallaxView]를 override 한다. */
    public open val parallaxView: View? = null

    /** [item]이 null 인 경우에 대한 별도의 처리가 필요할 경우, 이 함수를 override 한다. */
    public open fun bindItemNullable(item: Item?) {
        itemView.isGone = (item == null)

        if(item == null) {
            return
        }

        this.item = item
        bindItem(item)
    }

    /**
     * [ArkViewHolder]에 실제 [Item]을 바인딩한다.
     * @see ArkAdapter.onBindViewHolder
     */
    public abstract fun bindItem(item: Item)

    /**
     * [ArkViewHolder]가 window 에 attach 시 호출된다.
     * @see ArkAdapter.onViewAttachedToWindow
     */
    public open fun onAttachedToWindow() { /* does nothing on base */ }

    /**
     * [ArkViewHolder]가 window 로 부터 detach 시 호출된다.
     * @see ArkAdapter.onViewDetachedFromWindow
     */
    public open fun onDetachedFromWindow() { /* does nothing on base */ }

    /**
     * [RecyclerView]가 scroll(onScrolled)될 때 호출된다.
     * @see ArkAdapter.onScrollListener
     */
    public open fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) { /* does nothing on base */ }

    /**
     * [RecyclerView]의 scroll 상태가 변경(onScrollStateChanged)될 때 호출된다.
     * @see ArkAdapter.onScrollListener
     */
    public open fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) { /* does nothing on base */ }

    /**
     * [ArkViewHolder]가 recycle 될 시 호출된다.
     * @see ArkAdapter.onViewRecycled
     */
    public open fun onRecycled() { /* does nothing on base */ }
}