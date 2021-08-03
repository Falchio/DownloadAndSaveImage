package com.example.saveimageindatabase

import androidx.room.*

@Dao
interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(vararg image:ImageEntity)

    @Update
    fun updateImage(vararg image: ImageEntity)

    @Delete
    fun delete(vararg image: ImageEntity)

    @Query("SELECT * FROM imageentity")
    fun getAll(): List<ImageEntity>
}