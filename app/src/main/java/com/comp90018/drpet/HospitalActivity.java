package com.comp90018.drpet;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import java.util.List;
import java.util.Map;

import com.comp90018.drpet.helper.HospitalRetriever;

public class HospitalActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker previousMarker = null;
    private Map<String, Marker> makerMap;

    private ViewPager2 mapViewPager;
    private RecyclerView mapRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> hospitalsList;
    private Map<String, LatLng> hospitals;

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


    }

    private void loadHospitals() {

        // add dummy hospitals
//        hospitals.put("Melbourne Mobile Vet Service", new LatLng(-37.815202, 144.963940));
//        hospitals.put("First Paw Mobile Vet", new LatLng(-37.799459, 144.974074));
//        hospitals.put("Lort Smith Animal Hospital", new LatLng(-37.798866, 144.953180));

        final ArrayList<LatLng> locations = new ArrayList<>();
        locations.add(new LatLng(-37.815202, 144.963940));
        locations.add(new LatLng(-37.799459, 144.974074));
        locations.add(new LatLng(-37.798866, 144.953180));

        HospitalRetriever retriever = new HospitalRetriever();
        retriever.retrievData(new HospitalRetriever.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Hospital> list) {
                System.out.println(list.size());
                for (int i = 0; i < list.size(); i++) {
                    String hospitalName = list.get(i).getHospitalName();
//                    LatLng coord = getLocationFromAddress(list.get(i).getHospitalAddress());
                    LatLng coord = locations.get(i);
                    System.out.println(hospitalName + coord.toString());
                    hospitals.put(hospitalName, coord);
                }

                hospitalsList = new ArrayList<>(hospitals.keySet());

                initializeViewPager();
                createMapMarkers();
            }
        });

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

                markerClicked(marker);

                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                marker.showInfoWindow();

            }
        });

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

        // move the camera to current user location
        LatLng melbourne = new LatLng(-37.81, 144.96);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 14.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapViewPager.setVisibility(View.GONE);
            }
        });
    }

    private void createMapMarkers() {

        makerMap = new HashMap<>();

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
                markerClicked(marker);

                mapViewPager.setVisibility(View.VISIBLE);
                String chosenMarker = marker.getTitle();
                final int page = hospitalsList.indexOf(chosenMarker);
                mapViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mapViewPager.setCurrentItem(page, true);
                    }
                }, 100);
                return false;
            }
        });
    }

    // highlight the marker when clicked
    private void markerClicked(Marker marker) {
        if (previousMarker != null) {
            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        previousMarker = marker;
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng coord = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            coord = new LatLng((double) (location.getLatitude() * 1E6), (double) (location.getLongitude() * 1E6));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coord;
    }

}
