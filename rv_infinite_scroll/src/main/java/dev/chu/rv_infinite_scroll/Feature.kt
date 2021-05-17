package dev.chu.rv_infinite_scroll

import androidx.annotation.DrawableRes

data class Feature(
    @DrawableRes val iconResource: Int,
    val contentDescription: String
)