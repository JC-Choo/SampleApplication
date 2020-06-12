package dev.chu.memo.ui.recycler_multi_viewtype

import dev.chu.memo.R
import dev.chu.memo.ui.recycler_multi_viewtype.entity.SingleChoiceQuestion

data class SingleChoiceViewType(
    private val model: SingleChoiceQuestion
): ViewType<SingleChoiceQuestion> {
    override fun layoutId(): Int = R.layout.layout_single_choice
    override fun data(): SingleChoiceQuestion = model
}