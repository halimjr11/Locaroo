package com.halimjr11.locaroo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey()
    @ColumnInfo("id")
    val id: Long? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("location")
    val location: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @ColumnInfo("date")
    val date: String? = null
)
