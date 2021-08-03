package com.example.saveimageindatabase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.lifecycle.*
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL
import java.nio.ByteBuffer

class MainViewModel : ViewModel(), LifecycleObserver {
    private val bitmapMutableData: MutableLiveData<Bitmap> = MutableLiveData()
    val bitmapLiveData: LiveData<Bitmap> get() = bitmapMutableData

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun startDownloadImage() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {

                    val context = App.getAppContext()
                    val db = Room.databaseBuilder(context, ImageDatabase::class.java, "imageentity")
                        .build()
                    val imageDAO = db.imageDao()

                    val url =
                        URL("http://orgp.spb.ru/wp-content/uploads/2019/11/%D0%BB%D0%B0%D0%B7%D1%83%D1%80%D0%BD%D1%8B%D0%B91.jpg")
                    val stream = url.openConnection().getInputStream()
                    val bitmap = BitmapFactory.decodeStream(stream)

                    val appPicturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // путь к приватной папке, которая будет удалена вместе с приложением, доступны так же другие типы папок
                    val fileForSave = File(appPicturesDir, "file_name.jpg")
                    val outInputStream = FileOutputStream(fileForSave)
                    outInputStream.use {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                        it.flush()
                    }

                    val image = ImageEntity(1,"name", url.path, fileForSave.toString())
                    imageDAO.insertImage(image)

                    val listImage = imageDAO.getAll()
                    for (imageInc in listImage){
                        val bitmapInc = BitmapFactory.decodeFile(imageInc.filePath)
                        bitmapMutableData.postValue(bitmapInc)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    /**
     * Convert bitmap to byte array using ByteBuffer.
     */
    fun Bitmap.convertToByteArray(): ByteArray {
        //minimum number of bytes that can be used to store this bitmap's pixels
        val size = this.byteCount

        //allocate new instances which will hold bitmap
        val buffer = ByteBuffer.allocate(size)
        val bytes = ByteArray(size)

        //copy the bitmap's pixels into the specified buffer
        this.copyPixelsToBuffer(buffer)

        //rewinds buffer (buffer position is set to zero and the mark is discarded)
        buffer.rewind()

        //transfer bytes from buffer into the given destination array
        buffer.get(bytes)

        //return bitmap's pixels
        return bytes
    }


}