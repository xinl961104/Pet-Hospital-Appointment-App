package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import com.comp90018.drpet.ui.main.SectionsPagerAdapter;

public class ManagementActivity extends AppCompatActivity {

    // Click listener for choosing different navigation tabs
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                // To show Booking
                case R.id.navigation_linear: {
                    Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
                    startActivity(intent);
                    return true;
                }
                // To show Management
                case R.id.navigation_relative: {
//                    Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
//                    startActivity(intent);
                    return true;
                }
                // To show My Pet
                case R.id.navigation_list: {
                    Intent intent = new Intent(getApplicationContext(), ViewAllPetsActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Make a new appointment", Snackbar.LENGTH_LONG)
                        .setAction("Find Hospital", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        // Setting for Navigation Bar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_relative);
    }
}