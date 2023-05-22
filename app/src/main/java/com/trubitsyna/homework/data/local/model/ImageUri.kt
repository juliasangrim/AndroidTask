package com.trubitsyna.homework.data.local.model

import android.net.Uri
import java.util.UUID

data class ImageUri(
    val id: String = UUID.randomUUID().toString(),
    val uri: Uri,
)