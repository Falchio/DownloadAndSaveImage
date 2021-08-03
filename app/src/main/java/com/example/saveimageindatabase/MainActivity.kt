package com.example.saveimageindatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycle.addObserver(viewModel)

        val image:ImageView = findViewById(R.id.image)

        viewModel.bitmapLiveData.observe(this, {
            image.setImageBitmap(it)
        })
    }
}