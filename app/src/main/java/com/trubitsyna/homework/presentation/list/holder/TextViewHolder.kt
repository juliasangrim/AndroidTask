package com.trubitsyna.homework.presentation.list.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.databinding.ItemNoteWithTextBinding

class TextViewHolder(
    private val binding: ItemNoteWithTextBinding,
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
        }
    }
}