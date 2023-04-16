package com.trubitsyna.homework.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date
import java.util.UUID

@Parcelize
data class PostData(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val date: Date,
    val imageUrl: String,
    val mainText: String,
    val likeCount: Int
) : Parcelable