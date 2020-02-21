package dev.chu.memo.data.local

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import dev.chu.memo.common.Const
import java.util.*

@Entity(tableName = Const.TABLE_NAME_1)
//@TypeConverters(ConverterDate::class)
data class MemoData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MEMO_ID)
    var memo_id: Int = 0,
    @ColumnInfo(name = MEMO_TITLE)
    var title: String? = null,
    @ColumnInfo(name = MEMO_CONTENT)
    var content: String? = null,
    @ColumnInfo(name = MEMO_IMAGE_URL)
    var imageUrls: List<ImageData>? = null,
    @ColumnInfo(name = MEMO_DATA)
    val created: Date
) {
    companion object {
        const val MEMO_ID = "memo_id"
        const val MEMO_TITLE = "title"
        const val MEMO_CONTENT = "content"
        const val MEMO_IMAGE_URL = "image_urls"
        const val MEMO_DATA = "created"

//        fun to(item: ItemMemo): MemoData {
//            return MemoData(
//                title = item.title,
//                content = item.content,
//                listImageData = item.imageUrl,
//                created = Date()
//            )
//        }
    }
}

@Entity(
    tableName = Const.TABLE_NAME_2,
    foreignKeys = [
        ForeignKey(
            entity = MemoData::class,
            parentColumns = ["memo_id"],
            childColumns = ["image_id"],
            onDelete = CASCADE
        )
    ]
)
data class ImageData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = IMAGE_ID)
    var image_id: Int = 0,
    @ColumnInfo(name = IMAGE_URL)
    var imageUrl: String? = null
) {
    companion object {
        const val IMAGE_ID = "image_id"
        const val IMAGE_URL = "image_url"
    }
}