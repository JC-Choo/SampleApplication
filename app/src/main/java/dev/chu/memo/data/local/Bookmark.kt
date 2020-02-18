package dev.chu.memo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "Bookmark")
@TypeConverters(DateConverter::class)
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "user_id") var user_id: String?,
    @ColumnInfo(name = "login") var login: String?,
    @ColumnInfo(name = "avatar_url") var avatar_url: String?,
    @ColumnInfo(name = "is_book_mark") var is_book_mark : Boolean = false,
    @ColumnInfo(name = "created") val created: Date
) {
    companion object {
//        fun to(item: ItemResult): Bookmark {
//            return Bookmark(
//                user_id = item.userId,
//                login = item.login,
//                avatar_url = item.avatarUrl,
//                is_book_mark = item.isBookmark,
//                created = Date()
//            )
//        }
    }
}