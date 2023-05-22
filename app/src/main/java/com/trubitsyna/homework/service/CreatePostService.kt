package com.trubitsyna.homework.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.trubitsyna.homework.R
import com.trubitsyna.homework.domain.content.GetContentUriUseCase
import com.trubitsyna.homework.domain.post.CreatePostUseCase
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostService : Service(), CoroutineScope by MainScope() {

    companion object {
        private const val ARG_TEXT_KEY = "ARG_TEXT_KEY"
        private const val ARG_IMAGES_KEY = "ARG_IMAGES_KEY"
        private const val NOTIFICATION_ID = 777
        private const val ACTION_CREATE_POST = "ACTION_CREATE_POST"
        private const val CHANNEL_ID = "Create Notification"

        fun newIntent(
            context: Context,
            text: String?,
            list: List<Uri>,
        ) = Intent(context, CreatePostService::class.java).apply {
            action = ACTION_CREATE_POST
            putExtra(ARG_TEXT_KEY, text)
            putParcelableArrayListExtra(ARG_IMAGES_KEY, ArrayList(list))
        }
    }

    @Inject
    lateinit var getContentUriUseCase: GetContentUriUseCase

    @Inject
    lateinit var createPostUseCase: CreatePostUseCase

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        intent?.let {
            startForeground(NOTIFICATION_ID, createNotification())
            if (it.action == ACTION_CREATE_POST) {
                val text = it.extras?.getString(ARG_TEXT_KEY)
                val images = it.extras?.getParcelableArrayList<Uri>(ARG_IMAGES_KEY)?.map { uri ->
                    getContentUriUseCase.execute(uri)
                }
                images?.filterNotNull()?.let { it1 -> Log.i("Service", it1.count().toString()) }

                launch {
                    try {
                        createPostUseCase.execute(text, images?.filterNotNull())

                        this@CreatePostService.showToastError(R.string.creation_post_success)
                        stopSelf()
                    } catch (e: UnknownHostException) {
                        this@CreatePostService.showToastError(R.string.error_msg_network)
                        stopSelf()
                    } catch (e: Throwable) {
                        this@CreatePostService.showToastError(R.string.error_msg_create_post)
                        stopSelf()
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.loading_data_title))
            .setSmallIcon(R.drawable.ic_add_image_24)
            .setProgress(0, 0, true)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_LOW
        ).setName(getString(R.string.loading_data))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)

    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}