package dev.chu.rv_restoring_scroll_position.store_position

/**
 * viewHolder 별 item 에 상속받아야 하는 sealed class
 */
sealed class Content(val id: String) {
    class Banner(id: String) : Content(id)
    class Carousel(id: String, val list: List<Int>) : Content(id)
}