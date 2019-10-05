package com.comp90018.drpet.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comp90018.drpet.Appointment;
import com.comp90018.drpet.AppointmentAdapter;
import com.comp90018.drpet.DetailsActivity;
import com.comp90018.drpet.Hospital;
import com.comp90018.drpet.R;
import com.comp90018.drpet.RecyclerItemClickListener;
import com.comp90018.drpet.helper.AppointmentRetriever;
import com.comp90018.drpet.helper.HospitalRetriever;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PendingFragment extends Fragment {

//    private ArrayList<Appointment> appointmentsList = new ArrayList<>();

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PendingFragment newInstance(int index) {
        PendingFragment fragment = new PendingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        AppointmentRetriever retriever = new AppointmentRetriever();
        retriever.retrievData(new AppointmentRetriever.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Appointment> list) {
                final ArrayList<Appointment> appointmentsList = new ArrayList<>();

                for (Appointment i: list) {
                    String status = i.getStatus();
                    if (status.equals("booked")) {
                        appointmentsList.add(i);
                    }
                }

                AppointmentAdapter appointmentAdapter = new AppointmentAdapter(appointmentsList);
                recyclerView.setAdapter(appointmentAdapter);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("appintmentID", appointmentsList.get(position).getAppointmentID());
                        intent.putExtra("hospitalName", appointmentsList.get(position).getHospitalName());
                        intent.putExtra("doctorFirstName", appointmentsList.get(position).getDoctorFirstName());
                        intent.putExtra("doctorLastName", appointmentsList.get(position).getDoctorLastName());
                        intent.putExtra("date", appointmentsList.get(position).getDate());
                        intent.putExtra("time", appointmentsList.get(position).getStartTime());
                        intent.putExtra("petName", appointmentsList.get(position).getPetName());
                        intent.putExtra("comment", appointmentsList.get(position).getComment());
                        intent.putExtra("status", appointmentsList.get(position).getStatus());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        // ...
                    }
                }));
                appointmentAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }




}