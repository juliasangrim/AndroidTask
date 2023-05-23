package com.trubitsyna.homework.presentation.list.holder

import android.view.View
import com.trubitsyna.homework.data.model.Note

interface NoteViewHolder {
    fun getContentView(): View
    fun getDeleteView(): View
    fun bind(item: Note)
}