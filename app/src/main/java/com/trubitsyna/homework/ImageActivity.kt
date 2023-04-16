package com.trubitsyna.homework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.trubitsyna.homework.adapter.ImageViewAdapter
import com.trubitsyna.homework.data.ImageData
import com.trubitsyna.homework.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {

    private val imageViewAdapter: ImageViewAdapter by lazy {
        ImageViewAdapter()
    }

    companion object {
        private const val IMAGE_ARG_KEY = "IMAGE_ARG_KEY"
        private const val COLUMN_NUM = 3

        fun createIntent(context: Context, imageList: List<String>?) =
            Intent(context, ImageActivity::class.java).apply {
                putExtra(IMAGE_ARG_KEY, imageList?.toTypedArray())
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listImage = intent.extras?.getStringArray(IMAGE_ARG_KEY)?.asList()?.map {
            ImageData(imageUrl = it)
        }
        imageViewAdapter.submitList(listImage)

        with(binding) {
            recyclerViewImages.adapter = imageViewAdapter
            recyclerViewImages.layoutManager = GridLayoutManager(this@ImageActivity, COLUMN_NUM)
        }

    }
}