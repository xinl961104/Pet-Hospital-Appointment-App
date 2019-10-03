package com.comp90018.drpet.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.comp90018.drpet.Appointment;
import com.comp90018.drpet.AppointmentAdapter;
import com.comp90018.drpet.R;
import com.comp90018.drpet.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private ArrayList<String> appointmentsList;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retriveAppointments();
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_management, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(appointmentsList, getActivity());
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
//                intent.putExtra("doctorID", position);
//                startActivity(intent);
//                Log.d("Name", "onItemClick: Done!");
                Toast.makeText(getActivity().getApplicationContext(), appointmentsList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
        return root;
    }


    String UID;
    DatabaseReference reff;
    Appointment[] appointment;

    private void retriveAppointments() {
        reff  = FirebaseDatabase.getInstance().getReference().child("AppointmentNew").child(UID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long times;
                if (dataSnapshot.exists()) {
                    times = (dataSnapshot.getChildrenCount());
                    //System.out.println(times);
                    appointment = new Appointment[(int)times];

                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        appointment[i] = new Appointment();
                        appointment[i].setAppointmentID(ds.child("appointmentID").getValue(String.class));
                        appointment[i].setComment(ds.child("comment").getValue(String.class));
                        appointment[i].setDate(ds.child("date").getValue(String.class));
                        appointment[i].setDoctorID(ds.child("doctorID").getValue(String.class));
                        appointment[i].setPetID(ds.child("petID").getValue(String.class));
                        appointment[i].setPetName(ds.child("petName").getValue(String.class));
                        appointment[i].setStartTime(ds.child("startTime").getValue(String.class));
                        appointment[i].setStatus(ds.child("status").getValue(String.class));
                        appointment[i].setUserEmail(ds.child("userEmail").getValue(String.class));
                        appointment[i].setUserID(ds.child("userID").getValue(String.class));
                        appointment[i].setUserName(ds.child("userName").getValue(String.class));
                        System.out.println(i);
                        System.out.println(appointment[i]);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(getActivity().getApplicationContext(), "success connect", Toast.LENGTH_LONG ).show();
    }

}