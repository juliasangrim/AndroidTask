package com.trubitsyna.homework.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.databinding.FragmentNotesListBinding
import com.trubitsyna.homework.presentation.list.adapter.NoteListAdapter
import com.trubitsyna.homework.presentation.list.callback.SwipeCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_notes_list) {

    private val binding by viewBinding(FragmentNotesListBinding::bind)
    private val viewModel by viewModels<NotesListViewModel>()

    @Inject
    lateinit var listAdapter: NoteListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
        with(binding) {
            toolbarListNotes.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search -> {
                        findNavController().navigate(
                            NoteListFragmentDirections.actionNoteListFragmentToSearchFragment()
                        )
                        true
                    }

                    else -> false
                }
            }
            recyclerViewListNotes.apply {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = listAdapter.apply {
                    setSwipeNoteCallback { note ->
                        viewModel.onDeleteClicked(note)
                    }
                }
                val touchHelper = ItemTouchHelper(
                    SwipeCallback<Note, ViewHolder>(
                        {
                            listAdapter.frontLayerSelector(it)
                        }, {
                            listAdapter.actionLayerSelector(it)
                        }, {
                            listAdapter.keySelector(it)
                        }, {
                            listAdapter.onSwipeDeleteItem(it)
                        }
                    )
                )
                touchHelper.attachToRecyclerView(this)
            }
            floatingActionButtonAdd.setOnClickListener {
                findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragmentToNoteAddFragment()
                )
            }
        }
        viewModel.notesListLiveData.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }
}