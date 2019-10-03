package com.comp90018.drpet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<com.comp90018.drpet.AppointmentAdapter.AppointmentViewHolder> {

    private Activity activity;
    private ArrayList<String> appointments;

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        public TextView hospitalTextView;
        public TextView timeTextView;


        AppointmentViewHolder(View itemView) {
            super(itemView);
            hospitalTextView = itemView.findViewById(R.id.hospitalTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

    }


    public AppointmentAdapter(ArrayList<String> appointments, Activity activity) {
        this.appointments = appointments;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    @Override
    public com.comp90018.drpet.AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.management_view_holder, parent, false);
        return new com.comp90018.drpet.AppointmentAdapter.AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final com.comp90018.drpet.AppointmentAdapter.AppointmentViewHolder holder, int position) {
//        String hospitalID = appointments.get(position);
//        holder.hospitalTextView.setText();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
