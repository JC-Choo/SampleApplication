package dev.chu.memo.etc.click

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.Lifecycle

class ClickBindingComponent(lifecycle: Lifecycle): DataBindingComponent {
    private val clickBinding: ClickBindingImpl = ClickBindingImpl(lifecycle)

    // 메서드 이름 만드는 규칙에 유의(ex - get+className)
    override fun getClickBinding(): ClickBindingImpl {
        return clickBinding
    }
}