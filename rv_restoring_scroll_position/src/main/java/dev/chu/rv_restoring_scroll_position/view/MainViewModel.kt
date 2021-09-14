package dev.chu.rv_restoring_scroll_position.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.chu.rv_restoring_scroll_position.store_position.Content

class MainViewModel : ViewModel() {

    private val _content = MutableLiveData<List<Content>>()
    val content: LiveData<List<Content>>
        get() = _content

    init {
        generateContent()
    }

    private fun generateContent() {
        val cards = listOf(1..8).flatten()
        val carousels = listOf(1..100).flatten().map {
            Content.Carousel((100+it).toString(), cards)
        }
        val banners = listOf(1..15).flatten().map {
            Content.Banner(it.toString())
        }

        val contents = mutableListOf<Content>().apply {
            addAll(carousels)
            addAll(banners.subList(1, banners.size))
            shuffle()
            add(0, banners[0])
        }

        _content.value = contents
    }
}