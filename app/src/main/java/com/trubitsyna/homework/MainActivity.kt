package com.trubitsyna.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.trubitsyna.homework.adapter.ImageCardAdapter
import com.trubitsyna.homework.adapter.PostAdapter
import com.trubitsyna.homework.adapter.ProfileAdapter
import com.trubitsyna.homework.data.ImageCardData
import com.trubitsyna.homework.data.PostData
import com.trubitsyna.homework.data.ProfileData
import com.trubitsyna.homework.databinding.ActivityMainBinding
import com.trubitsyna.homework.utils.Constants
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private lateinit var profile: ProfileData
    private lateinit var imageCard: ImageCardData
    private lateinit var postList: List<PostData>

    private val profileAdapter: ProfileAdapter by lazy {
        ProfileAdapter()
    }
    private val imageCardAdapter: ImageCardAdapter by lazy {
        ImageCardAdapter()
    }
    private val postAdapter: PostAdapter by lazy {
        PostAdapter()
    }

    init {
        profile = ProfileData(
            name = Constants.NAME,
            subtext = Constants.SUBTEXT,
            imageCount = Constants.IMAGE_COUNT,
            postCount = Constants.POST_COUNT,
            subscribeCount = Constants.SUBSCRIBE_COUNT
        )


        val mockImagesUrlList = listOf(
            Constants.IMAGE1_URL,
            Constants.IMAGE2_URL,
            Constants.IMAGE3_URL,
            Constants.IMAGE4_URL,
            Constants.IMAGE1_URL
        )

        imageCard = ImageCardData(listImagesUrl = mockImagesUrlList)

        postList = listOf<PostData>(
            PostData(
                name = Constants.NAME,
                date = Date(System.currentTimeMillis()),
                imageUrl = Constants.IMAGE1_URL,
                mainText = Constants.MAIN_TEXT,
                likeCount = Constants.LIKE_COUNT
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        profileAdapter.setItem(profile)
        with(imageCardAdapter) {
            setItem(imageCard)
            setCallback {
                startActivity(ImageActivity.createIntent(this@MainActivity, it))
            }
        }
        with(postAdapter) {
            submitList(postList)
            setCallback {
                startActivity(PostActivity.createIntent(this@MainActivity, it))
            }
        }
        val concatenatedAdapter = ConcatAdapter(profileAdapter, imageCardAdapter, postAdapter)

        with(binding) {
            recyclerViewConcat.adapter = concatenatedAdapter
        }

    }


}