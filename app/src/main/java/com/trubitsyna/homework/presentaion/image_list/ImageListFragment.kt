package com.trubitsyna.homework.presentaion.image_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentImageListBinding
import com.trubitsyna.homework.presentaion.adapter.PagingImageViewAdapter
import com.trubitsyna.homework.utils.applySystemBarsTopInset
import com.trubitsyna.homework.utils.replace
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_image_list) {

    private val binding: FragmentImageListBinding by viewBinding(FragmentImageListBinding::bind)
    private val viewModel by viewModels<ImageListViewModel>()

    @Inject
    lateinit var imageViewAdapter: PagingImageViewAdapter

    companion object {
        private const val COLUMN_NUM = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySystemBarsTopInset()
        with(binding) {

            with(toolBarImages) {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                recyclerViewImages.apply {
                    layoutManager = GridLayoutManager(
                        requireContext(),
                        COLUMN_NUM,
                        GridLayoutManager.VERTICAL,
                        false
                    )
                    imageViewAdapter.setCallback {
                        findNavController().navigate(
                            ImageListFragmentDirections.actionImageListFragmentToImageFragment(it.id)
                        )
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        imageViewAdapter.loadStateFlow.collectLatest { loadStates ->
                            when (loadStates.refresh) {
                                is LoadState.Loading -> {
                                    recyclerViewImages.replace(progressBarImage.root)
                                }

                                is LoadState.NotLoading -> {
                                    progressBarImage.root.replace(recyclerViewImages)
                                }

                                is LoadState.Error -> {
                                    requireContext().showToastError(R.string.error_msg_network)
                                }
                            }
                        }
                    }
                    adapter = imageViewAdapter
                }
            }
            viewModel.getUserId()

            viewModel.userIdLiveData.observe(viewLifecycleOwner) {
                it?.let { profileId ->
                    viewModel.loadImages(profileId)
                }
            }
            viewModel.imageListLiveData.observe(viewLifecycleOwner) {
                imageViewAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

}