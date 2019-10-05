package com.comp90018.drpet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.PlacesViewHolder> {

    private AppCompatActivity activity;
    private ArrayList<Hospital> hospitals;

    public class PlacesViewHolder extends RecyclerView.ViewHolder {

        public TextView hospitalNameTextView;
        public TextView infoTextView;
        public TextView addressTextView;
        public Button chooseButton;


        PlacesViewHolder(View itemView) {
            super(itemView);
            hospitalNameTextView = itemView.findViewById(R.id.hospitalNameTextView);
            infoTextView = itemView.findViewById(R.id.infoTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            chooseButton = itemView.findViewById(R.id.chooseButton);
        }

    }


    public HospitalAdapter(ArrayList<Hospital> hospitals, AppCompatActivity activity) {
        this.hospitals = hospitals;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_view_holder, parent, false);
        return new PlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlacesViewHolder holder, final int position) {
        String hospitalName = hospitals.get(position).getHospitalName();
        holder.hospitalNameTextView.setText(hospitalName);
        String background = hospitals.get(position).getHospitalBackground();
        holder.infoTextView.setText(background);
        String address = hospitals.get(position).getHospitalAddress();
        holder.addressTextView.setText(address);

        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChooseDoctorActivity.class);
                intent.putExtra("HospitalId", hospitals.get(position).getHospitalId());
                intent.putExtra("HospitalName", hospitals.get(position).getHospitalName());
                intent.putExtra("HospitalAddress", hospitals.get(position).getHospitalAddress());
                intent.putExtra("HospitalPhone", hospitals.get(position).getHospitalPhone());
                intent.putExtra("HospitalBackground", hospitals.get(position).getHospitalBackground());
                intent.putExtra("HospitalOpenHours", hospitals.get(position).getHospitalOpenhours());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
