package com.example.rickandmorty3

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty3.di.UniversalViewModelFactory
import com.example.rickandmorty3.fragment.CharactersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    // запровайдили
    @Inject
    lateinit var universalFactory: UniversalViewModelFactory

    private val viewModel: CharactersViewModel by viewModels { universalFactory }

    // устанавливаем навигационный контроллер (для этого id nav_graph должны совпадать с id bottom_menu)
    private val navController by lazy { findNavController(R.id.fl_container) }

    // начало программы
    override fun onCreate(savedInstanceState: Bundle?) {
        RickAndMortyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        configureViews()
        configureObservers()
    }

    private fun initViews() {
        // нижняя навигация, устанавливаем контроллер
        bottomNavigation = findViewById(R.id.bottom_nav)
    }

    private fun configureViews() {
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_character_info -> {
                    hideNav()
                }
                else -> showNav()
            }
        }
    }

    private fun configureObservers() {
        viewModel.navigation.observe(this, {
            // переменная = action.береём контент и возвращаем наблюдателю
            val directions = it.getContent() ?: return@observe
            // говорим куда я должен преключится
            navController.navigate(directions)
        })
    }

    private fun showNav() {
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideNav() {
        bottomNavigation.visibility = View.GONE
    }
}