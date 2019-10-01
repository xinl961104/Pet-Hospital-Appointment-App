package com.comp90018.drpet;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker previousMarker = null;

    private ViewPager2 mapViewPager;
    private RecyclerView mapRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> hospitalsList;
    private Map<String, LatLng> hospitals;
    private Map<String,Marker> makerMap;
//    Map<String, Map<String,LatLng>> hospitals = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hospitals = new HashMap<>();

        loadHospitals();

        initializeViewPager();

    }

    private void loadHospitals() {

        // add dummy hospitals
        hospitals.put("Melbourne Mobile Vet Service", new LatLng(-37.815202, 144.963940));
        hospitals.put("First Paw Mobile Vet", new LatLng(-37.799459, 144.974074));
        hospitals.put("Lort Smith Animal Hospital", new LatLng(-37.798866, 144.953180));

        hospitalsList = new ArrayList<>(hospitals.keySet());

    }

    // *************************** Use ViewPager *************************
    private void initializeViewPager() {

        mapViewPager = findViewById(R.id.mapViewPager);

        mAdapter = new HospitalAdapter(hospitalsList, this);

        mapViewPager.setAdapter(mAdapter);
        mapViewPager.setOrientation(androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL);

        // go to new marker when page changed
        mapViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                String hospitalName = hospitalsList.get(position);
                Marker marker = makerMap.get(hospitalName);

                if (previousMarker != null) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                previousMarker = marker;

                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                marker.showInfoWindow();

            }
        });

    }

    // *************************** Use RecyclerView *************************
//    private void initializeRecyclerView() {
//
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
//    }


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

        // move the camera to current user location
        LatLng melbourne = new LatLng(-37.81, 144.96);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));

        createMapMarkers();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapViewPager.setVisibility(View.GONE);
            }
        });
    }

    private void createMapMarkers() {

        makerMap=new HashMap<>();

        for (String hospital : hospitals.keySet()) {
            LatLng vet = hospitals.get(hospital);
            MarkerOptions markerOption = new MarkerOptions()
                    .position(vet)
                    .title(hospital);

            Marker maker = mMap.addMarker(markerOption);

            makerMap.put(hospital, maker);
        }

        // marker click function
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (previousMarker != null) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                previousMarker = marker;
                
                mapViewPager.setVisibility(View.VISIBLE);
                String chosenMarker = marker.getTitle();
                int page = hospitalsList.indexOf(chosenMarker);
                mapViewPager.setCurrentItem(page, true);
                return false;
            }
        });
    }

}
