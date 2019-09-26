package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private RecyclerView mapRecyclerView;

    Map<String, LatLng> hostpitals = new HashMap<>();
//    Map<String, Map<String,LatLng>> hostpitals = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapRecyclerView = findViewById(R.id.mapRecyclerView);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng melbourne = new LatLng(-37.81, 144.96);
        mMap.addMarker(new MarkerOptions().position(melbourne).title("Marker in Melbourne"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));

        // add a dummy hospital
        hostpitals.put("Melbourne Mobile Vet Service", new LatLng(-37.815202, 144.963940));

        for (String hospital : hostpitals.keySet()) {
            LatLng vet = hostpitals.get(hospital);
            mMap.addMarker(new MarkerOptions()
                    .position(vet)
                    .title(hospital)).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vet));
        }

        // marker click function
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                int position = (int)(marker.getTag());
                mapRecyclerView.setVisibility(View.VISIBLE);
                Log.d("Marker", "Clicked!");
                //Using position get Value from arraylist
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapRecyclerView.setVisibility(View.GONE);
                Log.d("Map", "Short Clicked!");
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("Map", "Long Clicked!");
            }
        });
    }

}
