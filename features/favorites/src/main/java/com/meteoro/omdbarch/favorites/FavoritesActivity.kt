package com.meteoro.omdbarch.favorites

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.meteoro.omdbarch.components.binding.BindingActivity
import com.meteoro.omdbarch.favorites.databinding.ActivityFavoritesBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class FavoritesActivity : BindingActivity<ActivityFavoritesBinding>() {

    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var objectActivity: ObjetoComActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityFavoritesBinding {
        return ActivityFavoritesBinding.inflate(inflater)
    }

    override fun init() {
        setupBottomNavigationBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        setSupportActionBar(binding.toolbar)
        val bottomNavigationView = binding.bottomNav

        val navGraphIds = listOf(R.navigation.nav_movie, R.navigation.nav_words)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentMananger = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar
        controller.observe(
            this,
            Observer { navController ->
                setupActionBarWithNavController(navController)
            }
        )
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
