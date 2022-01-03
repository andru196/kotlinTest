package com.example.filmsearch.presentation.city

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filmsearch.R
import com.example.filmsearch.databinding.CityScreenBinding
import com.example.filmsearch.presentation.common.BaseFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment: BaseFragment(R.layout.city_screen) {
    private var isFineLocationGot: Boolean = false
    private val viewBinding by viewBinding(CityScreenBinding::bind)
    private val viewModel by viewModels<CityViewModel>()

    private var wasRationalDialogShown: Boolean = false
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                turnOnGeoSettings(true)
            } permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
            turnOnGeoSettings(false)
            }
            !wasRationalDialogShown && !shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
            -> showSettings();
        }
    }

    private val settingsRequestResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        requestLocation(isFineLocation = isFineLocationGot)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.cityRequestGeo.setOnClickListener { checkPermission() }

        viewModel.cityState.observe(viewLifecycleOwner) {
            viewBinding.cityCurrent.text = "${it.name} ${it.country}"
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )  == PackageManager.PERMISSION_GRANTED -> {
                turnOnGeoSettings(true)
            }
            ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                turnOnGeoSettings(false)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRationalDialog()
            } shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION) -> {
            showRationalDialog()
        }
            else ->{
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
            .setPositiveButton("OK") {_, _ -> requestPermission()}
            .setNegativeButton("Отмена") {_, _ ->}
            .create()
            .show()
    }

    private fun requestPermission() {
        wasRationalDialogShown = shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
                || shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionRequest.launch(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION))

    }

    private fun turnOnGeoSettings(isFineLocation: Boolean) {
        val locationRequest = createLocationRequest(isFineLocation)
        val locationManager = requireContext().getSystemService<LocationManager>() ?:  return

        if (LocationManagerCompat.isLocationEnabled(locationManager)) {
            requestLocation(isFineLocation)
            return
        }
        val settingsClient = LocationServices.getSettingsClient(requireContext())
        val settingsRequest = LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)
            .build()
        settingsClient.checkLocationSettings(settingsRequest)
            .addOnSuccessListener {
                requestLocation(isFineLocation)
            }
            .addOnFailureListener{ exception ->
                if (exception is ResolvableApiException) {
                    isFineLocationGot = isFineLocation
                    settingsRequestResult.launch(
                        IntentSenderRequest.Builder(exception.resolution).build())

                }
            }
    }

    //@RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    @SuppressLint("MissingPermission")
    private fun requestLocation(isFineLocation:Boolean) {
        Toast.makeText(requireContext(), "requestLocation", Toast.LENGTH_SHORT).show()
        val locationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
        val request = createLocationRequest(isFineLocation)
        val callback = object: LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                Toast.makeText(
                    requireContext(),
                    "latitude: ${p0.lastLocation.latitude} longitude: ${p0.lastLocation.longitude}",
                    Toast.LENGTH_SHORT)
                .show()
                viewModel.onLocationResult(p0.lastLocation.latitude, p0.lastLocation.longitude)
                locationProvider.removeLocationUpdates(this)
            }
        }

        locationProvider.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    private fun createLocationRequest(isFineLocation: Boolean): LocationRequest =
        LocationRequest.create().apply {
            interval = 5 * 1000L
            fastestInterval = 2 * 1000L
            priority = if (isFineLocation)
                LocationRequest.PRIORITY_HIGH_ACCURACY
            else
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        }

}
