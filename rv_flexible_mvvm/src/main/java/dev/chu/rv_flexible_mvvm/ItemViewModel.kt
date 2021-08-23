package dev.chu.rv_flexible_mvvm

import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlin.random.Random

/**
 * ItemViewModel
 * - list item level 에서 MVVM 아키텍처의 ViewModel 부분을 나타내는 인터페이스
 * - AAC 에서의 ViewModel 의 서브클래스는 아니지만, 옵저버블이 될 수 있다.
 */
interface ItemViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
        get() = layoutId.hashCode()
}

data class HeaderViewModel(val title: String) : ItemViewModel {
    override val layoutId: Int = R.layout.item_header
//    override val viewType: Int = CarListViewModel.HEADER_ITEM
}

class CarAdViewModel(
    val make: String,
    val model: String,
    val price: String,
    @get:Bindable var borderColor: Int
) : BaseObservable(), ItemViewModel {
    override val layoutId: Int = R.layout.item_car_listing_ad
//    override val viewType: Int = CarListViewModel.AD_ITEM

    fun onClickCarPrice() {
        borderColor = getRandomColor()
        notifyPropertyChanged(BR.borderColor)
    }

    private fun getRandomColor(): Int {
        return when (Random.nextInt(0, 3)) {
            0 -> R.drawable.background_ad_green
            1 -> R.drawable.background_ad_blue
            else -> R.drawable.background_ad_red
        }
    }
}

class CarListingViewModel(val make: String, val model: String, val price: String) : ItemViewModel {
    override val layoutId: Int = R.layout.item_car_listing
//    override val viewType: Int = CarListViewModel.LISTING_ITEM
}