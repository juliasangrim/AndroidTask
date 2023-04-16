package com.trubitsyna.homework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.trubitsyna.homework.data.PostData
import com.trubitsyna.homework.databinding.ActivityPostBinding
import com.trubitsyna.homework.utils.Constants
import java.text.SimpleDateFormat

class PostActivity : AppCompatActivity() {

    companion object {
        private const val POST_ARG_KEY = "POST_ARG_KEY"

        fun createIntent(context: Context, data: PostData) =
            Intent(context, PostActivity::class.java).apply {
                putExtra(POST_ARG_KEY, data)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.extras?.getParcelable(POST_ARG_KEY) as PostData?

        with(binding) {
            with(layoutViewCardPost) {
                textViewName.text = data?.name
                val dateFormatter = SimpleDateFormat(Constants.DATE_PATTERN)
                textViewDate.text = data?.date?.let { dateFormatter.format(it) }
                textViewPost.text = data?.mainText
                imageViewPost.load(data?.imageUrl)
                buttonLike.text = data?.likeCount.toString()
            }
        }
    }
}