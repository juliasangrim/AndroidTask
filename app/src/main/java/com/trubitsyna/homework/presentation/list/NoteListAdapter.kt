package com.trubitsyna.homework.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.trubitsyna.homework.data.Note
import com.trubitsyna.homework.databinding.ItemNoteBinding

class NoteListAdapter : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(diffUtil) {

    private var onSwipeNote: (String) -> Unit = {}

    fun setSwipeNoteCallback(callback: (String) -> Unit) {
        onSwipeNote = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : ViewHolder(binding.root) {
        fun bind(item: Note) {
            with(binding) {
                root.setOnLongClickListener {
                    onSwipeNote(item.id)
                    false
                }
                textView.text = item.text
            }

        }
    }
}

val diffUtil = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
}