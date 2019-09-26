package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
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

    private ViewPager2 mapViewPager;
    private RecyclerView mapRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<String> hospitalsList;
    Map<String, LatLng> hostpitals;
//    Map<String, Map<String,LatLng>> hostpitals = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hostpitals = new HashMap<>();

        // add a dummy hospital
        hostpitals.put("Melbourne Mobile Vet Service", new LatLng(-37.815202, 144.963940));
        hostpitals.put("First Paw Mobile Vet", new LatLng(-37.799459, 144.974074));
        hostpitals.put("Lort Smith Animal Hospital", new LatLng(-37.798866, 144.953180));

        hospitalsList = new ArrayList<>(hostpitals.keySet());

        // *************************** Use ViewPager *************************

        mapViewPager = findViewById(R.id.mapViewPager);

        mAdapter = new HospitalAdapter(hospitalsList, this);

        mapViewPager.setAdapter(mAdapter);

        mapViewPager.setOrientation(androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL);
        mapViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        // *************************** Use RecyclerView *************************
//        mapRecyclerView = findViewById(R.id.mapRecyclerView);
//        mapRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mapRecyclerView.setLayoutManager(layoutManager);
//
//        SnapHelper mSnapHelper = new PagerSnapHelper();
//        mSnapHelper.attachToRecyclerView(mapRecyclerView);
//
//        // specify an adapter (see also next example)
//        mAdapter = new HospitalAdapter(hospitalsList);
//        mapRecyclerView.setAdapter(mAdapter);
//        mapRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mapRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
////                intent.putExtra("hospitalID", position);
////                startActivity(intent);
//                Log.d("Name", "onItemClick: Done!");
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                // ...
//            }
//        }));
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));

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
//                mapRecyclerView.setVisibility(View.VISIBLE);
//                Log.d("Marker", "Clicked!");
//                String chosenMarker = marker.getTitle();
//                // Using chosenMarker get position from arraylist
//                int position = hospitalsList.indexOf(chosenMarker);
//                mapRecyclerView.smoothScrollToPosition(position);
                mapViewPager.setVisibility(View.VISIBLE);
                String chosenMarker = marker.getTitle();
                int page = hospitalsList.indexOf(chosenMarker);
                mapViewPager.setCurrentItem(page, true);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                mapRecyclerView.setVisibility(View.GONE);
                mapViewPager.setVisibility(View.GONE);
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
