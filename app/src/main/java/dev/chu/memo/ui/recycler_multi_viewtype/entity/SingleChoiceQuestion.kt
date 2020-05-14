package dev.chu.memo.ui.recycler_multi_viewtype.entity

data class SingleChoiceQuestion (
    val question: String,
    val optionOne: String,
    val optionTwo: String,
    var isOptionOneSelected: Boolean? = false
)
