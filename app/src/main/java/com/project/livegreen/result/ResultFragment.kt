package com.project.livegreen.result

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.project.livegreen.R
import com.project.livegreen.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val binding get() = _binding!!

    private lateinit var resultViewModel : ResultViewModel

    private lateinit var viewModelFactory : ResultViewModeFactory

    private var lon : Double = 0.0

    private var lat : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 101
            )
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                when {
                    location != null -> {
                        lon = location.longitude
                        lat = location.latitude
                        Log.d("loctestV", lon.toString())
                        Log.d("loctestV", lat.toString())
                        initialize()
                    }
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.whatToDoButton.setOnClickListener{
            findNavController().navigate(R.id.action_resultFragment_to_verdictFragment)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    Toast.makeText(requireContext(), "We need your location to provide info", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialize() {
        viewModelFactory = ResultViewModeFactory(lon, lat)
        resultViewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)
        resultViewModel.carbonData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.loadingimg.visibility = View.GONE
                binding.carbonIntensityTextview.text = it.data.carbonIntensity.toString().plus(" ").plus(it.units.carbonIntensity)
                binding.fossilTextview.text = it.data.fossilFuelPercentage.toString()
                binding.locationTextview.text = it.countryCode
            }
        })
    }
}