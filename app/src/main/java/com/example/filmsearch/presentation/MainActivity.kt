package com.example.filmsearch.presentation

import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.MainActivityBinding
import com.example.filmsearch.presentation.common.BaseActivity
import com.example.filmsearch.presentation.topFilms.TopFilmsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    private val viewModel by viewModels<MainActivityViewModel>()
    private val viewBinding by viewBinding(MainActivityBinding::bind)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val fragment = TopFilmsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()

            viewModel.favoritesCountState.observe(this) {
                viewBinding.mainActivityBottom.getOrCreateBadge(R.id.bottom_menu_favorites).number = it
            }
        }
    }
}

