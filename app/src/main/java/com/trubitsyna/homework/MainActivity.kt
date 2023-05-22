package com.trubitsyna.homework

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.trubitsyna.homework.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getToken()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.authFragment, R.id.createPostFragment, R.id.imageFragment -> hideBottomNav()
                else -> showBottomNav()

            }
        }
        viewModel.tokenLiveData.observe(this) {
            val navGraph = if (it != null) {
                navController.navInflater.inflate(R.navigation.nav_graph)
            } else {
                navController.navInflater.inflate(R.navigation.auth_nav_graph)
            }
            navController.graph = navGraph
            binding.bottomNavigationView.setupWithNavController(navController)
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE

    }
}