package dev.chu.memo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavTvShows(
    @PrimaryKey
    val name: String,
    val rating: Int,
    val finished: Boolean
)