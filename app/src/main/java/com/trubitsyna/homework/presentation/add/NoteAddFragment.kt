package com.trubitsyna.homework.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentAddNoteBinding

class NoteAddFragment: Fragment(R.layout.fragment_add_note) {

    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModel by viewModels<NoteAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            floatingActionButtonAdd.setOnClickListener {
                viewModel.onAddClicked(editTextAddNote.text.toString())
                findNavController().popBackStack()
            }
            toolbarAddNote.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}