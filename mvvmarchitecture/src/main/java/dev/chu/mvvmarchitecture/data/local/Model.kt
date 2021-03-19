package dev.chu.mvvmarchitecture.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

const val TableName = "catImage"

/**
 * MVVM 의 Model
 * model 은 기본적으로 데이터를 저장하는 클래스.
 *
 * 고양이 이미지를 로딩하는 동안 imageId 와 imageUrl 을 모델에 저장할 것이다.
 * json data 는 호출된 width 와 height 를 파라미터로 가지지만 또한 해당 앱에선 필요가 없다.
 *
 * [Entity] 는 기본적으로 database 의 "table"을 표현하기 위해 모델과 함께 사용된다.
 * 직렬화된 이름은 API 에서 받은 json 응답에 일차하는 변수 이름이다.
 */
@Entity(tableName = TableName, indices = [Index(value = arrayOf("id"), unique = true)])
data class Model (
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "image")
    var url: String? = null
)