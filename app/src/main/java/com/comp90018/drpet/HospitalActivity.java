package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

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


        hostpitals.put("Melbourne Mobile Vet Service", new LatLng(-37.815202, 144.963940));

        for (String hospital : hostpitals.keySet()){
            LatLng vet = hostpitals.get(hospital);
            mMap.addMarker(new MarkerOptions().position(vet).title(hospital)).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vet));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}

//public class MapFragment extends Fragment {
//
//    public MapFragment() {
//    }
//
//    public static MapFragment newInstance() {
//        return new MapFragment();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_map, container, false);
//
//        initViews(view);
//
//        return view;
//    }
//
//    private void initViews(View view) {
//        // TODO: Init views.
//    }
//}