package dev.chu.memo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.chu.memo.data.ItemMemo
import java.util.*

@Entity(tableName = "Memo")
@TypeConverters(DateConverter::class)
data class Memo(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "image_url") var image_url: String?,
    @ColumnInfo(name = "created") val created: Date
) {
    companion object {
        fun to(item: ItemMemo): Memo {
            return Memo(
                title = item.title,
                content = item.content,
                image_url = item.imageUrl,
                created = Date()
            )
        }
    }
}