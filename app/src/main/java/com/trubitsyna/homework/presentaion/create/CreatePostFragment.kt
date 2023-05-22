package com.trubitsyna.homework.presentaion.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.local.model.ImageUri
import com.trubitsyna.homework.databinding.FragmentCreatePostBinding
import com.trubitsyna.homework.presentaion.adapter.ImageCreatePostViewAdapter
import com.trubitsyna.homework.service.CreatePostService
import com.trubitsyna.homework.utils.Constants
import com.trubitsyna.homework.utils.doOnApplyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatePostFragment : Fragment(R.layout.fragment_create_post) {
    private val binding by viewBinding(FragmentCreatePostBinding::bind)

    @Inject
    lateinit var imageCreatePostViewAdapter: ImageCreatePostViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.doOnApplyWindowInsets { view, insets, padding ->
            val topPadding = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.updatePadding(
                top = padding.top + topPadding
            )
            insets
        }
        with(binding) {
            with(toolbarNewPost) {
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.createPost -> {
                            if (binding.editTextAddPost.text.toString().isNotBlank()
                                || imageCreatePostViewAdapter.currentList.isNotEmpty()
                            ) {
                                val serviceIntent = CreatePostService.newIntent(requireContext(),
                                    binding.editTextAddPost.text.toString(),
                                    imageCreatePostViewAdapter.currentList.map { image ->
                                        image.uri
                                    })
                                requireContext().startService(
                                    serviceIntent
                                )
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.error_msg_empty_post),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            true
                        }

                        else -> false
                    }
                }
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }
            recyclerViewImages.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = imageCreatePostViewAdapter
            }

            val pickImage =
                registerForActivityResult(
                    ActivityResultContracts.PickMultipleVisualMedia(Constants.MAX_IMAGE_ITEM)
                ) { uris ->
                    if (uris.isNotEmpty()) {
                        val listUris = uris.map { uri -> ImageUri(uri = uri) }.toMutableList()
                        imageCreatePostViewAdapter.submitList(
                            listUris.take(Constants.MAX_IMAGE_ITEM)
                        )
                    }
                }
            chipAddImage.setOnClickListener {
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

        }

    }
}