package com.example.filmsearch.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import com.example.filmsearch.R
import com.example.filmsearch.di.NetworkModule
import com.example.filmsearch.presentation.common.BaseActivity
import com.example.filmsearch.presentation.topFilms.TopFilmsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val fragment = TopFilmsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()

        }
    }
}