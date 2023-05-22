package com.trubitsyna.homework.domain.content

import android.content.ContentResolver
import android.net.Uri
import javax.inject.Inject

class GetContentUriUseCase @Inject constructor(
    private val contentResolver: ContentResolver
) {

    fun execute(uri: Uri): ByteArray? {
        return contentResolver.openInputStream(uri).use {
            it?.readBytes()
        }
    }
}