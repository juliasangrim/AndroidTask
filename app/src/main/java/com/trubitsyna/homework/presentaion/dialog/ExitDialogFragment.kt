package com.trubitsyna.homework.presentaion.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.trubitsyna.homework.R
import com.trubitsyna.homework.databinding.FragmentDialogExitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExitDialogFragment : DialogFragment() {
    private val viewModel by viewModels<ExitDialogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = MaterialAlertDialogBuilder(it)
            val inflater = requireActivity().layoutInflater
            val binding = FragmentDialogExitBinding.inflate(inflater)
            builder.setView(binding.root)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    viewModel.deleteUserData()
                    val navController = findNavController()
                    val authGraph =
                        navController.navInflater.inflate(R.navigation.auth_nav_graph)
                    navController.graph = authGraph
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    findNavController().popBackStack()
                }
            builder.create()
        }
    }
}