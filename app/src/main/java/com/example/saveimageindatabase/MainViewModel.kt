package com.example.saveimageindatabase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.example.saveimageindatabase.App
import java.lang.Exception
import java.net.URL

class MainViewModel : ViewModel(), LifecycleObserver {
    private val bitmapMutableData: MutableLiveData<Bitmap> = MutableLiveData()
    val bitmapLiveData: LiveData<Bitmap> get() = bitmapMutableData

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun startDownloadImage() {
        try {
            Thread {
                val context = App().applicationContext
                val db = Room.databaseBuilder(context, ImageDatabase::class.java, "imageentity")
                    .build()
                val imageDAO = db.imageDao()

                val url =
                    URL("http://orgp.spb.ru/wp-content/uploads/2019/11/%D0%BB%D0%B0%D0%B7%D1%83%D1%80%D0%BD%D1%8B%D0%B91.jpg")
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                Log.e("TAG", "startDownloadImage: ${bitmap.byteCount}")
                bitmapMutableData.postValue(bitmap)
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}