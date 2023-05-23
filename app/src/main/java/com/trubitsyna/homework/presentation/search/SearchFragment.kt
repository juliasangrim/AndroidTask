package com.trubitsyna.homework.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentSearchNotesBinding
import com.trubitsyna.homework.presentation.list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search_notes) {

    private val binding by viewBinding(FragmentSearchNotesBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var listAdapter: NoteListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllNotes()
        with(binding) {
            textInputSearchInput.editText?.doOnTextChanged { searchQuery, _, _, _ ->
                viewModel.onSearchClicked(searchQuery.toString())

            }
            layoutToolbarBack.toolbarAddNote.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            recyclerViewListSearchNotes.apply {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = listAdapter
            }
            viewModel.searchNoteListLiveData.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
            }
        }
    }
}