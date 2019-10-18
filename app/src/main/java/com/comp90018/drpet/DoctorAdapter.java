package com.comp90018.drpet;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorsViewHolder> {

    private AppCompatActivity activity;
    private ArrayList<String> doctors;
    private ArrayList<Bitmap> doctorPictures;

    public class DoctorsViewHolder extends RecyclerView.ViewHolder {

        public TextView doctorNameTextView;
        public ImageView doctorImageView;


        DoctorsViewHolder(View itemView) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.doctorNameTextView);
            doctorImageView = itemView.findViewById(R.id.doctorImageView);
        }

    }


    public DoctorAdapter(ArrayList<String> doctors, AppCompatActivity activity) {
        this.doctors = doctors;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    @Override
    public DoctorAdapter.DoctorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view_holder, parent, false);
        return new DoctorAdapter.DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorAdapter.DoctorsViewHolder holder, int position) {
        String name = doctors.get(position);
        holder.doctorNameTextView.setText(name);
        holder.doctorImageView.setBackgroundResource(R.drawable.avatar_example_1);
        holder.doctorImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//        holder.doctorImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, AppointmentActivity.class);
//                intent.putExtra("doctorName", holder.doctorNameTextView.getText());
//                activity.startActivity(intent);
//            }
//        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
