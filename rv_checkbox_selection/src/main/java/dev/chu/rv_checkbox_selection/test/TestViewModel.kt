package dev.chu.rv_checkbox_selection.test

import androidx.lifecycle.ViewModel

class TestViewModel: ViewModel() {
    val videoAdapter = TestAdapter().apply {
        setItems(getItems())
    }

    private fun getItems() = mutableListOf<Test>().apply {
        for (i in 0 until 5) {
            add(Test("title_$i", "name_$i"))
        }
    }
}