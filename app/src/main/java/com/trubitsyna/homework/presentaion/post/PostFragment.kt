package com.trubitsyna.homework.presentaion.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.databinding.FragmentPostBinding
import com.trubitsyna.homework.presentaion.adapter.ImageViewAdapter
import com.trubitsyna.homework.utils.applySystemBarsTopInset
import com.trubitsyna.homework.utils.replace
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post) {

    private val binding: FragmentPostBinding by viewBinding(FragmentPostBinding::bind)
    private val viewModel by viewModels<PostViewModel>()
    private val args by navArgs<PostFragmentArgs>()

    @Inject
    lateinit var dateFormatter: SimpleDateFormat

    @Inject
    lateinit var imageViewAdapter: ImageViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySystemBarsTopInset()
        with(binding) {
            with(toolbarPost) {
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.toolbarDeleteButton -> {
                            viewModel.deletePost(args.postId)
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }
            }
            recyclerViewImages.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                imageViewAdapter.setCallback {
                    findNavController().navigate(
                        PostFragmentDirections.actionPostFragmentToImageFragment(it.id)
                    )
                }
                adapter = imageViewAdapter
            }
        }
        viewModel.getPost(args.postId)
        viewModel.postLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoadableResult.Loading -> {
                    binding.scrollViewPost.replace(binding.progressBarPost.root)
                }

                is LoadableResult.Success -> {
                    onSuccessLoadPost(result.data)
                }

                is LoadableResult.Error -> {
                    requireContext().showToastError(R.string.error_msg_network)
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

    private fun onSuccessLoadPost(post: Post) {
        with(binding) {
            imageViewProfile.load(post.owner.avatarUrl)
            textViewName.text = post.owner.displayName ?: post.owner.username
            textViewDate.text = dateFormatter.format(post.dateCreated)
            textViewPost.text = post.text
            buttonLike.text = post.likeCount.toString()
            imageViewAdapter.submitList(post.images)
            progressBarPost.root.replace(scrollViewPost)
        }
    }
}


