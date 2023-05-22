package com.trubitsyna.homework.presentaion.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentFeedBinding
import com.trubitsyna.homework.presentaion.adapter.PostAdapter
import com.trubitsyna.homework.utils.applySystemBarsTopInset
import com.trubitsyna.homework.utils.replace
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val viewModel by viewModels<FeedViewModel>()

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySystemBarsTopInset()
        viewModel.loadFeed()
        with(binding) {
            recyclerViewFeed.apply {
                postAdapter.setCallback {
                    findNavController().navigate(
                        FeedFragmentDirections.actionFeedFragmentToPostFragment(it.id)
                    )
                }
                adapter = postAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    postAdapter.loadStateFlow.collectLatest { loadStates ->
                        when (loadStates.refresh) {
                            is LoadState.Loading -> {
                                recyclerViewFeed.replace(progressBarFeed.root)
                            }

                            is LoadState.NotLoading -> {
                                progressBarFeed.root.replace(recyclerViewFeed)
                            }

                            is LoadState.Error -> {
                                requireContext().showToastError(R.string.error_msg_network)
                            }
                        }
                    }
                }
                layoutManager = LinearLayoutManager(
                    context
                )
            }

            floatActionButtonAdd.setOnClickListener {
                findNavController().navigate(
                    FeedFragmentDirections.actionFeedFragmentToCreatePostFragment()
                )
            }
        }
        viewModel.postLivaData.observe(viewLifecycleOwner) {
            postAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }
}