package com.trubitsyna.homework.presentaion.image

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.local.model.Image
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.databinding.FragmentImageItemBinding
import com.trubitsyna.homework.utils.doOnApplyWindowInsets
import com.trubitsyna.homework.utils.getMaxSizeImageUrl
import com.trubitsyna.homework.utils.replace
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_image_item) {
    private val binding by viewBinding(FragmentImageItemBinding::bind)
    private val viewModel by viewModels<ImageViewModel>()
    private val args by navArgs<ImageFragmentArgs>()

    @Inject
    lateinit var dateFormatter: SimpleDateFormat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            root.doOnApplyWindowInsets { view, insets, padding ->
                val topPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
                val bottomPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                view.updatePadding(
                    top = padding.top + topPadding,
                    bottom = padding.bottom + bottomPadding
                )
                insets
            }
            toolbarImage.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            toolbarImage.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.toolbarDeleteButton -> {
                        viewModel.deleteImage(args.imageId)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }

        }
        viewModel.getImage(args.imageId)

        viewModel.imageLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoadableResult.Loading -> {
                    binding.imageViewImage.replace(binding.progressBarImage.root)
                }

                is LoadableResult.Error -> {
                    requireContext().showToastError(R.string.error_msg_network)
                }

                is LoadableResult.Success -> {
                    onSuccessLoadImage(result.data)
                }
            }

        }
        viewModel.deleteResultLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoadableResult.Error -> {
                    requireContext().showToastError(R.string.error_msg_network)
                }

                is LoadableResult.Success -> {
                    if (!result.data) {
                        requireContext().showToastError(R.string.error_msg_delete)
                    } else {
                        requireContext().showToastError(R.string.delete_success)
                        findNavController().popBackStack()
                    }
                }

                else -> {/* no-op */
                }
            }
        }

    }

    private fun onSuccessLoadImage(image: Image) {
        with(binding) {
            textViewName.text = image.owner.displayName ?: image.owner.username
            textViewDate.text = dateFormatter.format(image.dateCreated)
            imageViewProfile.load(image.owner.avatarUrl)
            imageViewImage.load(image.getMaxSizeImageUrl())
            progressBarImage.root.replace(imageViewImage)
        }
    }
}