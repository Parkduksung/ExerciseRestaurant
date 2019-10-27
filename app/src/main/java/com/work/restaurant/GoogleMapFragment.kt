package com.work.restaurant

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class GoogleMapFragment : OnMapReadyCallback, Fragment() {


    private lateinit var mapView: MapView

    override fun onMapReady(p0: GoogleMap) {

        val SEOUL = LatLng(37.56, 126.97)
        val markerOptions = MarkerOptions()
        markerOptions.position(SEOUL)
        markerOptions.title("서울")
        markerOptions.snippet("한국의 수도")
        p0.addMarker(markerOptions)
        p0.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
        p0.animateCamera(CameraUpdateFactory.zoomTo(10f))
    }


    override fun onAttach(context: Context) {
        Log.d(fragmentName, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.google_maps, container, false)

        mapView = rootView.findViewById(R.id.map)
        mapView.getMapAsync(this)

        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(fragmentName, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        if (mapView != null)
            mapView.onCreate(savedInstanceState)

    }


    override fun onStart() {
        Log.d(fragmentName, "onStart")
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        Log.d(fragmentName, "onResume")
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        Log.d(fragmentName, "onPause")
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        Log.d(fragmentName, "onStop")
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        Log.d(fragmentName, "onDestroyView")
        super.onDestroyView()


    }

    override fun onDestroy() {
        Log.d(fragmentName, "onDestroy")
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onDetach() {
        Log.d(fragmentName, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val fragmentName = "GoogleMapFragment"
    }
}