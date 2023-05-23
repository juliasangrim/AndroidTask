package com.trubitsyna.homework.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentNotesListBinding

class NoteListFragment : Fragment(R.layout.fragment_notes_list) {

    private val binding by viewBinding(FragmentNotesListBinding::bind)
    private val viewModel by viewModels<NotesListViewModel>()

    private val listAdapter = NoteListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
        with(binding) {
            recyclerViewListNotes.apply {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = listAdapter.apply {
                    setSwipeNoteCallback { id ->
                        viewModel.onDeleteClicked(id)
                    }
                    //for the future
//                    val itemTouchHelper = ItemTouchHelper(SwipeDeleteCallback(adapter = this))
//                    itemTouchHelper.attachToRecyclerView(recyclerViewListNotes)
                }
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