package com.example.saveimageindatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ImageEntity::class), version = 1)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun imageDao():ImageDAO
}