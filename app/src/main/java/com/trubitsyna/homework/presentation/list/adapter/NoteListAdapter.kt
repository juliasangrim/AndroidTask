package com.trubitsyna.homework.presentation.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.databinding.ItemNoteImageBinding
import com.trubitsyna.homework.databinding.ItemNoteImageWithTextBinding
import com.trubitsyna.homework.databinding.ItemNoteWithTextBinding
import com.trubitsyna.homework.presentation.list.holder.ImageTextViewHolder
import com.trubitsyna.homework.presentation.list.holder.ImageViewHolder
import com.trubitsyna.homework.presentation.list.holder.NoteViewHolder
import com.trubitsyna.homework.presentation.list.holder.TextViewHolder
import com.trubitsyna.homework.presentation.list.holder.ViewHolderType
import javax.inject.Inject

class NoteListAdapter @Inject constructor() : ListAdapter<Note, ViewHolder>(NoteDiffUtil) {
    private var onSwipeNote: (Note) -> Unit = {}

    fun setSwipeNoteCallback(callback: (Note) -> Unit) {
        onSwipeNote = callback
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        if (isImageNote(item)) {
            return ViewHolderType.IMAGE_NOTE.ordinal
        }
        if (isTextNote(item)) {
            return ViewHolderType.TEXT_NOTE.ordinal
        }
        return ViewHolderType.IMAGE_TEXT_NOTE.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            ViewHolderType.IMAGE_NOTE.ordinal ->
                ImageViewHolder(
                    ItemNoteImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ViewHolderType.TEXT_NOTE.ordinal ->
                TextViewHolder(
                    ItemNoteWithTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            else ->
                ImageTextViewHolder(
                    ItemNoteImageWithTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        if (isImageNote(item)) {
            (holder as ImageViewHolder).bind(item)

        }
        if (isTextNote(item)) {
            (holder as TextViewHolder).bind(item)

        }
        if (isImageWithTextNote(item)) {
            (holder as ImageTextViewHolder).bind(item)

        }
    }

    fun onSwipeDeleteItem(note: Note) {
        onSwipeNote(note)
    }

    fun frontLayerSelector(viewHolder: ViewHolder): View =
        (viewHolder as NoteViewHolder).getContentView()

    fun actionLayerSelector(viewHolder: ViewHolder): View =
        (viewHolder as NoteViewHolder).getDeleteView()

    fun keySelector(viewHolder: ViewHolder): Note {
        return currentList[viewHolder.adapterPosition]
    }

    private fun isTextNote(note: Note) = note.text.isNotBlank() && note.imageUri == null

    private fun isImageNote(note: Note) = note.text.isBlank() && note.imageUri != null

    private fun isImageWithTextNote(note: Note) = note.text.isNotBlank() && note.imageUri != null
}