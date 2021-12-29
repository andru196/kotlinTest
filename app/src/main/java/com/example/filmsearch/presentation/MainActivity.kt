package com.example.filmsearch.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.filmsearch.R
import com.example.filmsearch.databinding.ActivityMainBinding
import com.example.filmsearch.di.NetworkModule
import com.example.filmsearch.presentation.common.BaseActivity
import com.example.filmsearch.presentation.topFilms.TopFilmsFragment

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
            val ai = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["keyValue"]
            NetworkModule.apiKey = value.toString()
        }
    }
}