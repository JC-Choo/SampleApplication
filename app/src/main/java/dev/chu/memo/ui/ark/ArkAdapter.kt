//package dev.chu.memo.ui.ark
//
//import android.view.ViewGroup
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.recyclerview.widget.ListUpdateCallback
//import androidx.recyclerview.widget.RecyclerView
//
//@Suppress("RedundantVisibilityModifier")
//public open class ArkAdapter(
//    viewHolderFactory: ViewHolderFactory,
//    vararg _pagingHelpers: PagingHelper
//): RecyclerView.Adapter<ArkViewHolder<ListItem<*>>>(), ListUpdateCallback {
//    /** 생성자를 통해 주입된 [PagingHelper]들의 list. */
//    private val pagingHelpers = _pagingHelpers.toList()
//
//    /** ViewHolder 작업을 관리할 manager. */
//    private val viewHolderManager = ViewHolderManager(viewHolderFactory, pagingHelpers)
//
//    /** [isListUpdateNotified]를 위한 내부 변수. */
//    private val _isListUpdateNotified = MutableLiveData<Event<Boolean>>()
//
//    /**
//     * list 갱신 후 [ArkAdapter]로 notified 되었는지 여부와, notified event observe 가 가능한 [LiveData].
//     * - [ArkAdapter.list] 갱신 이후 추가 처리가 필요할 경우, 해당 변수를 observe 한다.
//     */
//    public val isListUpdateNotified: LiveData<Event<Boolean>> = _isListUpdateNotified
//
//    /** [ArkAdapter]가 현재 사용중인 item list. */
//    public var list: List<ListItem<*>>?
//        get() = differ.currentList
//        set(value) {
//            _isListUpdateNotified.changeValue(Event(false))
//            latestList = value
//            val newList = if (isLoadMoreAdded) {
//                getListWithLoadMore()
//            } else {
//                value
//            }
//            differ.submitList(newList)
//        }
//
//    // region
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArkViewHolder<ListItem<*>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: ArkViewHolder<ListItem<*>>, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//    // endregion
//
//    // region ListUpdateCallback
//    override fun onInserted(position: Int, count: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onRemoved(position: Int, count: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onMoved(fromPosition: Int, toPosition: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onChanged(position: Int, count: Int, payload: Any?) {
//        TODO("Not yet implemented")
//    }
//    // endregion
//}