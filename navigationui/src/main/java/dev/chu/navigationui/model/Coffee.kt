package dev.chu.navigationui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Coffee 클래스는 각 커피에 대해 추적하는 데이터를 보유 : 커피의 이름, 설명, 등급
 */
@Entity
data class Coffee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String = "",
    val rating: Int
)