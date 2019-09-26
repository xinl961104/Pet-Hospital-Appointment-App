package com.comp90018.drpet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.PlacesViewHolder> {

    public class PlacesViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        PlacesViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.hospitalNameTextView);
        }

    }

    private ArrayList<String> hospitals;

    public HospitalAdapter(ArrayList<String> hospitals) {

        this.hospitals = hospitals;
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
    public void onBindViewHolder(PlacesViewHolder holder, int position) {
        String place = hospitals.get(position);

        holder.textView.setText(place);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
