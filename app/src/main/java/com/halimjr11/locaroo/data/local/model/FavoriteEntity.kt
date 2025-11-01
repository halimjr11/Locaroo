package com.halimjr11.locaroo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.halimjr11.locaroo.utils.Constant.FAVORITE_NAME

@Entity(FAVORITE_NAME)
data class FavoriteEntity(
    @PrimaryKey()
    @ColumnInfo("id")
    val id: Long? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("location")
    val location: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null
)
