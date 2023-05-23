package com.trubitsyna.homework.presentation.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.databinding.ItemNoteImageWithTextBinding

class ImageTextViewHolder(
    private val binding: ItemNoteImageWithTextBinding,
) : RecyclerView.ViewHolder(binding.root), NoteViewHolder {

    override fun getContentView(): View {
        return binding.cardViewContent
    }

    override fun getDeleteView(): View {
        binding.cardViewDelete.root.visibility = View.VISIBLE
        return binding.cardViewDelete.root
    }

    override fun bind(item: Note) {
        with(binding) {
            textViewNoteText.text = item.text
            imageViewNoteImage.load(item.imageUri)
        }
    }
}