package com.doce.cactus.saba.findnear.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doce.cactus.saba.findnear.MainActivity
import com.doce.cactus.saba.findnear.R
import com.doce.cactus.saba.findnear.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private var flashON = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)
        binding.mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        val mapController = binding.mapView.controller
        mapController.setZoom(20.0)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val marker = Marker(binding.mapView)
        marker.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_location_on_24, null)
        val overlayManager = binding.mapView.overlayManager
        overlayManager.add(marker)

        val rotationGestureOverlay = RotationGestureOverlay(binding.mapView)
        rotationGestureOverlay.isEnabled = true
        overlayManager.add(rotationGestureOverlay)

        binding.mapView.setMultiTouchControls(true)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val startPoint = GeoPoint(location.latitude, location.longitude)
                mapController.setCenter(startPoint)
                            }
        }

        binding.mapView.addMapListener(object :MapListener{
            override fun onScroll(event: ScrollEvent?): Boolean {
                val point = binding.mapView.mapCenter
                marker.position = GeoPoint(point.latitude,point.longitude)

                return true
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                return true
            }

        })
        overlayManager.add(MapEventsOverlay(object :MapEventsReceiver{
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                (requireActivity() as MainActivity).showSystemUI()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(4000)
                    (requireActivity() as MainActivity).hideSystemUI()
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                return true
            }

        }))
        binding.myUbicationIv.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val startPoint = GeoPoint(location.latitude, location.longitude)
                mapController.setCenter(startPoint)
            }
        }
        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val position = binding.mapView.mapCenter
            val locationUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${position.latitude},${position.longitude}")
            intent.putExtra(Intent.EXTRA_TEXT, locationUri.toString())
            startActivity(Intent.createChooser(intent, "Compartir ubicación"))
        }
        binding.colorBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_colorScreenFragment)
        }
        val cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        binding.flashBtn.setOnClickListener {
            val cameraId = cameraManager.cameraIdList[0]

            val hasFlash = requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

            if (hasFlash) {
                try{
                    flashON = if(!flashON){
                        cameraManager.setTorchMode(cameraId, true)
                        binding.flashBtn.setImageResource(R.drawable.ic_baseline_flashlight_off_24)
                        true

                    }else{
                        cameraManager.setTorchMode(cameraId, false)
                        binding.flashBtn.setImageResource(R.drawable.ic_baseline_flashlight_on_24)
                        false
                    }
                }catch (e: Error){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()

    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

