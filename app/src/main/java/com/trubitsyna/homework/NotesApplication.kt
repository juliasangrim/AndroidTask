package com.trubitsyna.homework

import android.app.Application

class NotesApplication: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: NotesApplication? = null

        fun getApplicationContext() = instance?.applicationContext
    }
}