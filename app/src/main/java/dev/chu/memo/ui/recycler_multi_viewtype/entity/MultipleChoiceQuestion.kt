package dev.chu.memo.ui.recycler_multi_viewtype.entity

data class MultipleChoiceQuestion (
    val question: String,
    val optionList: List<Answer>
)