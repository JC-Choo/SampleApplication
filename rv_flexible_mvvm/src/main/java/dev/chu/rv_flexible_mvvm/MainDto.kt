package dev.chu.rv_flexible_mvvm

import androidx.annotation.LayoutRes

interface ItemViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
        get() = 0
}

class HeaderViewModel(val title: String): ItemViewModel {
    override val layoutId: Int = R.layout.item_header
    override val viewType: Int = CarListViewModel.HEADER_ITEM
}

class CarAdViewModel(val make: String, val model: String, val price: String) : ItemViewModel {
    override val layoutId: Int = R.layout.item_car_listing_ad
    override val viewType: Int = CarListViewModel.AD_ITEM
}

class CarListingViewModel(val make: String, val model: String, val price: String) : ItemViewModel {
    override val layoutId: Int = R.layout.item_car_listing
    override val viewType: Int = CarListViewModel.LISTING_ITEM
}