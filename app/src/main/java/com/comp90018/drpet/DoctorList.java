package com.comp90018.drpet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author xinli
 * @date 2019-09-30
 */
public class DoctorList extends ArrayAdapter<DoctorModel> {

    private AppCompatActivity context;
    private List<DoctorModel> doctorList;

    public DoctorList(AppCompatActivity context, List<DoctorModel> doctorList){
        super(context, R.layout.doctorlist, doctorList);
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.doctorlist, null,true);

        TextView textViewDoctorName = (TextView) listViewItem.findViewById(R.id.DoctorName);

        DoctorModel doctor = doctorList.get(position);

        textViewDoctorName.setText(doctor.getDoctorFirstName()+" "+doctor.getDoctorLastName());

        return listViewItem;

    }
}
