package com.trubitsyna.homework.presentaion.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.trubitsyna.homework.R
import com.trubitsyna.homework.data.remote.model.LoadableResult
import com.trubitsyna.homework.databinding.FragmentProfileBinding
import com.trubitsyna.homework.presentaion.adapter.ImageCardAdapter
import com.trubitsyna.homework.presentaion.adapter.PostAdapter
import com.trubitsyna.homework.presentaion.adapter.ProfileAdapter
import com.trubitsyna.homework.utils.applySystemBarsTopInset
import com.trubitsyna.homework.utils.replace
import com.trubitsyna.homework.utils.showToastError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var profileAdapter: ProfileAdapter

    @Inject
    lateinit var imageCardAdapter: ImageCardAdapter

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.applySystemBarsTopInset()
        viewModel.getUserId()

        imageCardAdapter.setCallback {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToImageListFragment()
            )
        }

        postAdapter.setCallback {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToPostFragment2(it.id)
            )
        }

        val concatenatedAdapter = ConcatAdapter(profileAdapter, imageCardAdapter, postAdapter)
        with(binding) {
            recyclerViewConcat.adapter = concatenatedAdapter
            recyclerViewConcat.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            toolbarProfile.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.toolbarExitButton -> {
                        findNavController().navigate(
                            ProfileFragmentDirections.actionProfileFragmentToExitDialogFragment()
                        )
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            floatActionButtonAdd.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToCreatePostFragment()
                )
            }
        }

        viewModel.userIdLivaData.observe(viewLifecycleOwner) {
            viewModel.getProfile(it)
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoadableResult.Error -> {
                    requireContext().showToastError(R.string.error_msg_network)
                }

                is LoadableResult.Loading -> {
                    binding.recyclerViewConcat.replace(
                        binding.progressBarProfile.root
                    )
                }

                is LoadableResult.Success -> {
                    with(result.data) {
                        profileAdapter.setItem(this)
                        imageCardAdapter.setItem(images)
                        viewModel.loadPosts(id)
                    }
                    binding.progressBarProfile.root.replace(
                        binding.recyclerViewConcat
                    )
                }
            }

        }
        viewModel.postLiveData.observe(viewLifecycleOwner) {
            postAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }
}