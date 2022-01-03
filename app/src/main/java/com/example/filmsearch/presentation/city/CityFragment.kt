package com.example.filmsearch.presentation.city

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.CityScreenBinding
import com.example.filmsearch.presentation.common.BaseFragment
import java.util.jar.Manifest

class CityFragment: BaseFragment(R.layout.city_screen) {
    private val viewBinding by viewBinding(CityScreenBinding::bind)

    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                requestLocation()
            }
            !shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
            -> showSettings();
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.cityRequestGeo.setOnClickListener { getCity() }
    }

    private fun getCity() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )  == PackageManager.PERMISSION_GRANTED -> {
                requestLocation()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRationalDialog()
            } else ->{
                requestPermission()
            }
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${requireContext().packageName}")
        startActivity(intent)
    }

    private fun showSettings() {
        AlertDialog.Builder(requireContext())
            .setTitle("Настройки")
            .setMessage("Для определения местоположения необходимо дать разрешение")
            .setPositiveButton("OK") {_, _ -> openSettings()}
            .setNegativeButton("Отмена") {_, _ ->}
            .create()
            .show()
    }

    private fun showRationalDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Объяснение")
            .setMessage("Для определения местоположения необходимо дать разрешение")
            .setPositiveButton("OK") {_, _ -> requestLocation()}
            .setNegativeButton("Отмена") {_, _ ->}
            .create()
            .show()
    }

    private fun requestPermission() {
        permissionRequest.launch(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun requestLocation() {
        Toast.makeText(requireContext(), "requestLocation", Toast.LENGTH_SHORT).show()
    }
}