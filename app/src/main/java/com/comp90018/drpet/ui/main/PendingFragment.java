package com.comp90018.drpet.ui.main;

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
import com.comp90018.drpet.R;
import com.comp90018.drpet.RecyclerItemClickListener;
import com.comp90018.drpet.helper.AppointmentRetriever;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PendingFragment extends Fragment {

    private ArrayList<Appointment> appointmentsList;

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
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(appointmentsList, getActivity());
        recyclerView.setAdapter(appointmentAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
//                intent.putExtra("doctorID", position);
//                startActivity(intent);
//                Log.d("Name", "onItemClick: Done!");
//                Toast.makeText(getActivity().getApplicationContext(), appointmentsList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

        appointmentAdapter.notifyDataSetChanged();
        return root;
    }




}