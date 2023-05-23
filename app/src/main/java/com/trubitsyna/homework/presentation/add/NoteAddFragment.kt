package com.trubitsyna.homework.presentation.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteAddFragment : Fragment(R.layout.fragment_add_note) {

    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private val viewModel by viewModels<NoteAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pickMedia =
            registerForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                if (uri != null) {
                    viewModel.saveImageInternalStorage(uri)
                }
            }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.deleteImageInternalStorage()
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        with(binding) {
            floatingActionButtonAdd.setOnClickListener {
                val noteText = editTextAddNote.text.toString()
                if (viewModel.hasNoteImageOrText(noteText)) {
                    viewModel.onAddClicked(
                        editTextAddNote.text.toString()
                    )
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        context, getString(R.string.error_msg_add_note),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            layoutToolbarBack.toolbarAddNote.setNavigationOnClickListener {
                viewModel.deleteImageInternalStorage()
                findNavController().popBackStack()
            }
            chipAddImage.setOnClickListener {
                pickMedia.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
            imageViewCancelButton.setOnClickListener {
                imageViewAddImage.setImageDrawable(null)
                viewModel.deleteImageInternalStorage()
            }
        }

        viewModel.imageLiveData.observe(viewLifecycleOwner) {
            binding.imageViewAddImage.load(it)
            if (it == null) {
                makeAddImageButtonVisible()
            } else {
                makeImageVisible()
            }
        }
    }

    private fun makeImageVisible() {
        with(binding) {
            imageViewCancelButton.visibility = View.VISIBLE
            imageViewAddImage.visibility = View.VISIBLE
            chipAddImage.visibility = View.INVISIBLE
        }
    }

    private fun makeAddImageButtonVisible() {
        with(binding) {
            imageViewCancelButton.visibility = View.INVISIBLE
            imageViewAddImage.visibility = View.INVISIBLE
            chipAddImage.visibility = View.VISIBLE
        }
    }
}