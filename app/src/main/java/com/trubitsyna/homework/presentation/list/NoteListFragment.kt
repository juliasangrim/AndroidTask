package com.trubitsyna.homework.presentation.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentNotesListBinding
import com.trubitsyna.homework.presentation.list.adapter.NoteListAdapter
import com.trubitsyna.homework.presentation.search.SearchFragment
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