package dev.chu.memo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dev.chu.memo.common.Const

data class RepoSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)

@Entity(tableName = Const.TABLE_NAME_3)
data class Repo(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("html_url")
    val url: String,
    @field:SerializedName("stargazers_count")
    val stars: String,
    @field:SerializedName("forks_count")
    val forks: String,
    @field:SerializedName("language")
    val language: String
)