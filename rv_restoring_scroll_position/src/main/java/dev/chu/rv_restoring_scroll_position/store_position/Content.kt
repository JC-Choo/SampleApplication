package dev.chu.rv_restoring_scroll_position.store_position

sealed class Content(val id: String) {
    class Banner(id: String) : Content(id)
    class Carousel(id: String, val list: List<Int>) : Content(id)
}