package com.example.saveimageindatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "image_link") val imageLink: String,
    @ColumnInfo(name = "file_path") val filePath: String

)